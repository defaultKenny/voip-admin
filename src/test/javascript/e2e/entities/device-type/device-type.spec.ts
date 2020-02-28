import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DeviceTypeComponentsPage, DeviceTypeDeleteDialog, DeviceTypeUpdatePage } from './device-type.page-object';

const expect = chai.expect;

describe('DeviceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceTypeComponentsPage: DeviceTypeComponentsPage;
  let deviceTypeUpdatePage: DeviceTypeUpdatePage;
  let deviceTypeDeleteDialog: DeviceTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeviceTypes', async () => {
    await navBarPage.goToEntity('device-type');
    deviceTypeComponentsPage = new DeviceTypeComponentsPage();
    await browser.wait(ec.visibilityOf(deviceTypeComponentsPage.title), 5000);
    expect(await deviceTypeComponentsPage.getTitle()).to.eq('voipAdminApp.deviceType.home.title');
  });

  it('should load create DeviceType page', async () => {
    await deviceTypeComponentsPage.clickOnCreateButton();
    deviceTypeUpdatePage = new DeviceTypeUpdatePage();
    expect(await deviceTypeUpdatePage.getPageTitle()).to.eq('voipAdminApp.deviceType.home.createOrEditLabel');
    await deviceTypeUpdatePage.cancel();
  });

  it('should create and save DeviceTypes', async () => {
    const nbButtonsBeforeCreate = await deviceTypeComponentsPage.countDeleteButtons();

    await deviceTypeComponentsPage.clickOnCreateButton();
    await promise.all([deviceTypeUpdatePage.setNameInput('name')]);
    expect(await deviceTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await deviceTypeUpdatePage.save();
    expect(await deviceTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deviceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DeviceType', async () => {
    const nbButtonsBeforeDelete = await deviceTypeComponentsPage.countDeleteButtons();
    await deviceTypeComponentsPage.clickOnLastDeleteButton();

    deviceTypeDeleteDialog = new DeviceTypeDeleteDialog();
    expect(await deviceTypeDeleteDialog.getDialogTitle()).to.eq('voipAdminApp.deviceType.delete.question');
    await deviceTypeDeleteDialog.clickOnConfirmButton();

    expect(await deviceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
