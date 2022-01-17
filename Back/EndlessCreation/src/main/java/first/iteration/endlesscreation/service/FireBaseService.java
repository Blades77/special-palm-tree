package first.iteration.endlesscreation.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import first.iteration.endlesscreation.Model.FirebaseCredential;
import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;


import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class FireBaseService {


    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/<bucket name>/o/%s?alt=media";

    private final Environment environment;
    private final GroupDataService groupDataService;
    private final UserService userService;
    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;


    public FireBaseService(Environment environment, GroupDataService groupDataService, UserService userService) {
        this.environment = environment;
        this.groupDataService = groupDataService;
        this.userService = userService;
    }

    @PostConstruct
    private void initializeFirebase() throws Exception {
        bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
        projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");

        InputStream firebaseCredential = createFirebaseCredential();
        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(firebaseCredential)).build();

    }


    public String uploadFile(MultipartFile multipartFile) throws IOException{

        File file = convertMultiPartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);

        Storage storage = storageOptions.getService();

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        file.delete();
        System.out.println("File " + file.toPath() + " uploaded to bucket " + bucketName + " as " + objectName);
        return objectName;

    }

//    public Object upload222(MultipartFile multipartFile) {
//
//        try {
//            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
//            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
//
//            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
//            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
//            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
//            System.out.println(TEMP_URL);
//            return null;                  // Your customized response
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ResourceNotFoundException("Nie udało się");
//        }
//
//    }


//    private String uploadFile(File file, String fileName) throws IOException {
//        BlobId blobId = BlobId.of("endless-creation7.appspot.com", fileName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
//        Storage storage = StorageOptions.newBuilder().setCredentials(getCredentials()).build().getService();
//        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
//    }
//
//    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
//        File tempFile = new File(fileName);
//        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//            fos.write(multipartFile.getBytes());
//            fos.close();
//        }
//        return tempFile;
//    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }


    public Object uploadGroupImage(MultipartFile multipartFile, Long groupId) {
        String uploadedFileName = null;
        try {
            uploadedFileName = uploadFile(multipartFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupDataEntity groupDataEntity = groupDataService.findById(groupId);
        groupDataEntity.setImageLink(uploadedFileName);
        groupDataService.saveGroupDataEntity(groupDataEntity);

        return null; //response
    }

    public Object uploadUserImage(MultipartFile multipartFile){
        String uploadedFileName = null;
        try {
            uploadedFileName = uploadFile(multipartFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        UserEntity userEntity = userService.getUserEntityByName(LoggedUserGetter.getUsser());
        userEntity.setImageLink(uploadedFileName);
        userService.saveUserEntity(userEntity);
        return null;
    }

    public void deleteGroupImage(Long groupId){
        GroupDataEntity groupDataEntity = groupDataService.findById(groupId);
        groupDataEntity.setImageLink(null);
        groupDataService.saveGroupDataEntity(groupDataEntity);
    }
    public void deleteUserImage(){
        UserEntity userEntity = userService.getUserEntityByName(LoggedUserGetter.getUsser());
        userEntity.setImageLink(null);
        userService.saveUserEntity(userEntity);
    }



    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteFile() {
        try {
            Storage storage = StorageOptions.newBuilder().setCredentials(getCredentials()).build().getService();
            storage.delete("endless-creation7.appspot.com", "cbd4c1b3-a896-44d5-8d20-41f6edbcefb3.jpg");
            System.out.println("usunieto");
        } catch (Exception e) {
            System.out.println("inputstream nie działa");

        }
    }

    private Credentials getCredentials() {
        try {
            return GoogleCredentials.fromStream(createFirebaseCredential());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private InputStream createFirebaseCredential() throws Exception {
        //private key
        String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        FirebaseCredential firebaseCredential = new FirebaseCredential();
        firebaseCredential.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredential.setProject_id(environment.getRequiredProperty("FIREBASE_PROJECT_ID"));
        firebaseCredential.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
        firebaseCredential.setPrivate_key(privateKey);
        firebaseCredential.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredential.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredential.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredential.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredential.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredential.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

        //serialize with Jackson
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(firebaseCredential);

        //convert jsonString string to InputStream using Apache Commons
        return IOUtils.toInputStream(jsonString);
    }

}