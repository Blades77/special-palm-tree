import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';

export function appInitializer(authenticationService: AuthenticationService) {
    return () => new Promise(resolve => {
        // attempt to refresh token on app start up to auto authenticate
        console.log("tu atemptuje")
        if(authenticationService.getRefreshToken()){
            authenticationService.refreshToken(authenticationService.getRefreshToken())
            .subscribe()
            .add(resolve);
        }
        
    });
}


