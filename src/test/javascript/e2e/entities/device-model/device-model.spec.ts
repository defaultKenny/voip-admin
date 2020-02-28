import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DeviceModelComponentsPage,
  /* DeviceModelDeleteDialog,
   */ DeviceModelUpdatePage
} from './device-model.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('DeviceModel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceModelComponentsPage: DeviceModelComponentsPage;
  let deviceModelUpdatePage: DeviceModelUpdatePage;
  /* let deviceModelDeleteDialog: DeviceModelDeleteDialog; */
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeviceModels', async () => {
    await navBarPage.goToEntity('device-model');
    deviceModelComponentsPage = new DeviceModelComponentsPage();
    await browser.wait(ec.visibilityOf(deviceModelComponentsPage.title), 5000);
    expect(await deviceModelComponentsPage.getTitle()).to.eq('voipAdminApp.deviceModel.home.title');
  });

  it('should load create DeviceModel page', async () => {
    await deviceModelComponentsPage.clickOnCreateButton();
    deviceModelUpdatePage = new DeviceModelUpdatePage();
    expect(await deviceModelUpdatePage.getPageTitle()).to.eq('voipAdminApp.deviceModel.home.createOrEditLabel');
    await deviceModelUpdatePage.cancel();
  });

  /*  it('should create and save DeviceModels', async () => {
        const nbButtonsBeforeCreate = await deviceModelComponentsPage.countDeleteButtons();

        await deviceModelComponentsPage.clickOnCreateButton();
        await promise.all([
            deviceModelUpdatePage.setNameInput('name'),
            deviceModelUpdatePage.setLinesCountInput('5'),
            deviceModelUpdatePage.setConfigFileInput(absolutePath),
            deviceModelUpdatePage.setFirmwareFileInput(absolutePath),
            // deviceModelUpdatePage.additionalOptionsSelectLastOption(),
            deviceModelUpdatePage.deviceTypeSelectLastOption(),
        ]);
        expect(await deviceModelUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        const selectedIsConfigurable = deviceModelUpdatePage.getIsConfigurableInput();
        if (await selectedIsConfigurable.isSelected()) {
            await deviceModelUpdatePage.getIsConfigurableInput().click();
            expect(await deviceModelUpdatePage.getIsConfigurableInput().isSelected(), 'Expected isConfigurable not to be selected').to.be.false;
        } else {
            await deviceModelUpdatePage.getIsConfigurableInput().click();
            expect(await deviceModelUpdatePage.getIsConfigurableInput().isSelected(), 'Expected isConfigurable to be selected').to.be.true;
        }
        expect(await deviceModelUpdatePage.getLinesCountInput()).to.eq('5', 'Expected linesCount value to be equals to 5');
        expect(await deviceModelUpdatePage.getConfigFileInput()).to.endsWith(fileNameToUpload, 'Expected ConfigFile value to be end with ' + fileNameToUpload);
        expect(await deviceModelUpdatePage.getFirmwareFileInput()).to.endsWith(fileNameToUpload, 'Expected FirmwareFile value to be end with ' + fileNameToUpload);
        await deviceModelUpdatePage.save();
        expect(await deviceModelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await deviceModelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DeviceModel', async () => {
        const nbButtonsBeforeDelete = await deviceModelComponentsPage.countDeleteButtons();
        await deviceModelComponentsPage.clickOnLastDeleteButton();

        deviceModelDeleteDialog = new DeviceModelDeleteDialog();
        expect(await deviceModelDeleteDialog.getDialogTitle())
            .to.eq('voipAdminApp.deviceModel.delete.question');
        await deviceModelDeleteDialog.clickOnConfirmButton();

        expect(await deviceModelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
