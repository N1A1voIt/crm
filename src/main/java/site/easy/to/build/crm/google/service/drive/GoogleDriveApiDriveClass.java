package site.easy.to.build.crm.google.service.drive;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.service.user.OAuthUserService;


public class GoogleDriveApiDriveClass extends GoogleDriveApiServiceImpl{
    public GoogleDriveApiDriveClass(OAuthUserService oAuthUserService) {
        super(oAuthUserService);
    }

}
