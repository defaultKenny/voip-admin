import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DeviceSettingComponentsPage,
  /* DeviceSettingDeleteDialog,
   */ DeviceSettingUpdatePage
} from './device-setting.page-object';

const expect = chai.expect;

describe('DeviceSetting e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceSettingComponentsPage: DeviceSettingComponentsPage;
  let deviceSettingUpdatePage: DeviceSettingUpdatePage;
  /* let deviceSettingDeleteDialog: DeviceSettingDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeviceSettings', async () => {
    await navBarPage.goToEntity('device-setting');
    deviceSettingComponentsPage = new DeviceSettingComponentsPage();
    await browser.wait(ec.visibilityOf(deviceSettingComponentsPage.title), 5000);
    expect(await deviceSettingComponentsPage.getTitle()).to.eq('voipAdminApp.deviceSetting.home.title');
  });

  it('should load create DeviceSetting page', async () => {
    await deviceSettingComponentsPage.clickOnCreateButton();
    deviceSettingUpdatePage = new DeviceSettingUpdatePage();
    expect(await deviceSettingUpdatePage.getPageTitle()).to.eq('voipAdminApp.deviceSetting.home.createOrEditLabel');
    await deviceSettingUpdatePage.cancel();
  });

  /*  it('should create and save DeviceSettings', async () => {
        const nbButtonsBeforeCreate = await deviceSettingComponentsPage.countDeleteButtons();

        await deviceSettingComponentsPage.clickOnCreateButton();
        await promise.all([
            deviceSettingUpdatePage.setValueInput('value'),
            deviceSettingUpdatePage.deviceSelectLastOption(),
            deviceSettingUpdatePage.additionalOptionSelectLastOption(),
        ]);
        expect(await deviceSettingUpdatePage.getValueInput()).to.eq('value', 'Expected Value value to be equals to value');
        await deviceSettingUpdatePage.save();
        expect(await deviceSettingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await deviceSettingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DeviceSetting', async () => {
        const nbButtonsBeforeDelete = await deviceSettingComponentsPage.countDeleteButtons();
        await deviceSettingComponentsPage.clickOnLastDeleteButton();

        deviceSettingDeleteDialog = new DeviceSettingDeleteDialog();
        expect(await deviceSettingDeleteDialog.getDialogTitle())
            .to.eq('voipAdminApp.deviceSetting.delete.question');
        await deviceSettingDeleteDialog.clickOnConfirmButton();

        expect(await deviceSettingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
