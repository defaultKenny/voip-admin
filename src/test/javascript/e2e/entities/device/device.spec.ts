import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DeviceComponentsPage,
  /* DeviceDeleteDialog,
   */ DeviceUpdatePage
} from './device.page-object';

const expect = chai.expect;

describe('Device e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceComponentsPage: DeviceComponentsPage;
  let deviceUpdatePage: DeviceUpdatePage;
  /* let deviceDeleteDialog: DeviceDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Devices', async () => {
    await navBarPage.goToEntity('device');
    deviceComponentsPage = new DeviceComponentsPage();
    await browser.wait(ec.visibilityOf(deviceComponentsPage.title), 5000);
    expect(await deviceComponentsPage.getTitle()).to.eq('voipAdminApp.device.home.title');
  });

  it('should load create Device page', async () => {
    await deviceComponentsPage.clickOnCreateButton();
    deviceUpdatePage = new DeviceUpdatePage();
    expect(await deviceUpdatePage.getPageTitle()).to.eq('voipAdminApp.device.home.createOrEditLabel');
    await deviceUpdatePage.cancel();
  });

  /*  it('should create and save Devices', async () => {
        const nbButtonsBeforeCreate = await deviceComponentsPage.countDeleteButtons();

        await deviceComponentsPage.clickOnCreateButton();
        await promise.all([
            deviceUpdatePage.setMacInput('mac'),
            deviceUpdatePage.setInventoryInput('inventory'),
            deviceUpdatePage.setLocationInput('location'),
            deviceUpdatePage.setHostnameInput('hostname'),
            deviceUpdatePage.setWebAccessLoginInput('webAccessLogin'),
            deviceUpdatePage.setWebAccessPasswordInput('webAccessPassword'),
            deviceUpdatePage.setIpAddressInput('ipAddress'),
            deviceUpdatePage.setSubnetMaskInput('subnetMask'),
            deviceUpdatePage.setDefaultGatewayInput('defaultGateway'),
            deviceUpdatePage.setDns1Input('dns1'),
            deviceUpdatePage.setDns2Input('dns2'),
            deviceUpdatePage.setProvUrlInput('provUrl'),
            deviceUpdatePage.provProtocolSelectLastOption(),
            deviceUpdatePage.deviceModelSelectLastOption(),
            deviceUpdatePage.responsiblePersonSelectLastOption(),
        ]);
        expect(await deviceUpdatePage.getMacInput()).to.eq('mac', 'Expected Mac value to be equals to mac');
        expect(await deviceUpdatePage.getInventoryInput()).to.eq('inventory', 'Expected Inventory value to be equals to inventory');
        expect(await deviceUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
        expect(await deviceUpdatePage.getHostnameInput()).to.eq('hostname', 'Expected Hostname value to be equals to hostname');
        expect(await deviceUpdatePage.getWebAccessLoginInput()).to.eq('webAccessLogin', 'Expected WebAccessLogin value to be equals to webAccessLogin');
        expect(await deviceUpdatePage.getWebAccessPasswordInput()).to.eq('webAccessPassword', 'Expected WebAccessPassword value to be equals to webAccessPassword');
        const selectedDhcpEnabled = deviceUpdatePage.getDhcpEnabledInput();
        if (await selectedDhcpEnabled.isSelected()) {
            await deviceUpdatePage.getDhcpEnabledInput().click();
            expect(await deviceUpdatePage.getDhcpEnabledInput().isSelected(), 'Expected dhcpEnabled not to be selected').to.be.false;
        } else {
            await deviceUpdatePage.getDhcpEnabledInput().click();
            expect(await deviceUpdatePage.getDhcpEnabledInput().isSelected(), 'Expected dhcpEnabled to be selected').to.be.true;
        }
        expect(await deviceUpdatePage.getIpAddressInput()).to.eq('ipAddress', 'Expected IpAddress value to be equals to ipAddress');
        expect(await deviceUpdatePage.getSubnetMaskInput()).to.eq('subnetMask', 'Expected SubnetMask value to be equals to subnetMask');
        expect(await deviceUpdatePage.getDefaultGatewayInput()).to.eq('defaultGateway', 'Expected DefaultGateway value to be equals to defaultGateway');
        expect(await deviceUpdatePage.getDns1Input()).to.eq('dns1', 'Expected Dns1 value to be equals to dns1');
        expect(await deviceUpdatePage.getDns2Input()).to.eq('dns2', 'Expected Dns2 value to be equals to dns2');
        expect(await deviceUpdatePage.getProvUrlInput()).to.eq('provUrl', 'Expected ProvUrl value to be equals to provUrl');
        await deviceUpdatePage.save();
        expect(await deviceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Device', async () => {
        const nbButtonsBeforeDelete = await deviceComponentsPage.countDeleteButtons();
        await deviceComponentsPage.clickOnLastDeleteButton();

        deviceDeleteDialog = new DeviceDeleteDialog();
        expect(await deviceDeleteDialog.getDialogTitle())
            .to.eq('voipAdminApp.device.delete.question');
        await deviceDeleteDialog.clickOnConfirmButton();

        expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
