import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';

export function appInitializer(authenticationService: AuthenticationService) {


        if(authenticationService.hasToken()){
            return () => new Promise(resolve => {
            const refreshToken = authenticationService.getRefreshToken();
            console.log("tu jak jest")
            authenticationService.refreshToken(refreshToken)
            .subscribe()
            .add(resolve);
        });
        }else{
            console.log("tu jak token nie jest null")
            return () => new Promise((resolve) => resolve(true));
        }
        
        

}


