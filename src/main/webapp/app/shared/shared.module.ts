import { NgModule } from '@angular/core';
import { VoipAdminSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { JhiLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { MacPipe } from './pipes/mac.pipe';

@NgModule({
  imports: [VoipAdminSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    MacPipe
  ],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    VoipAdminSharedLibsModule,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    MacPipe
  ]
})
export class VoipAdminSharedModule {}
