import { element, by, ElementFinder } from 'protractor';

export class DeviceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device div table .btn-danger'));
  title = element.all(by.css('jhi-device div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DeviceUpdatePage {
  pageTitle = element(by.id('jhi-device-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  macInput = element(by.id('field_mac'));
  inventoryInput = element(by.id('field_inventory'));
  locationInput = element(by.id('field_location'));
  hostnameInput = element(by.id('field_hostname'));
  webAccessLoginInput = element(by.id('field_webAccessLogin'));
  webAccessPasswordInput = element(by.id('field_webAccessPassword'));
  dhcpEnabledInput = element(by.id('field_dhcpEnabled'));
  ipAddressInput = element(by.id('field_ipAddress'));
  subnetMaskInput = element(by.id('field_subnetMask'));
  defaultGatewayInput = element(by.id('field_defaultGateway'));
  dns1Input = element(by.id('field_dns1'));
  dns2Input = element(by.id('field_dns2'));
  provUrlInput = element(by.id('field_provUrl'));
  provProtocolSelect = element(by.id('field_provProtocol'));
  deviceModelSelect = element(by.id('field_deviceModel'));
  responsiblePersonSelect = element(by.id('field_responsiblePerson'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMacInput(mac) {
    await this.macInput.sendKeys(mac);
  }

  async getMacInput() {
    return await this.macInput.getAttribute('value');
  }

  async setInventoryInput(inventory) {
    await this.inventoryInput.sendKeys(inventory);
  }

  async getInventoryInput() {
    return await this.inventoryInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return await this.locationInput.getAttribute('value');
  }

  async setHostnameInput(hostname) {
    await this.hostnameInput.sendKeys(hostname);
  }

  async getHostnameInput() {
    return await this.hostnameInput.getAttribute('value');
  }

  async setWebAccessLoginInput(webAccessLogin) {
    await this.webAccessLoginInput.sendKeys(webAccessLogin);
  }

  async getWebAccessLoginInput() {
    return await this.webAccessLoginInput.getAttribute('value');
  }

  async setWebAccessPasswordInput(webAccessPassword) {
    await this.webAccessPasswordInput.sendKeys(webAccessPassword);
  }

  async getWebAccessPasswordInput() {
    return await this.webAccessPasswordInput.getAttribute('value');
  }

  getDhcpEnabledInput() {
    return this.dhcpEnabledInput;
  }
  async setIpAddressInput(ipAddress) {
    await this.ipAddressInput.sendKeys(ipAddress);
  }

  async getIpAddressInput() {
    return await this.ipAddressInput.getAttribute('value');
  }

  async setSubnetMaskInput(subnetMask) {
    await this.subnetMaskInput.sendKeys(subnetMask);
  }

  async getSubnetMaskInput() {
    return await this.subnetMaskInput.getAttribute('value');
  }

  async setDefaultGatewayInput(defaultGateway) {
    await this.defaultGatewayInput.sendKeys(defaultGateway);
  }

  async getDefaultGatewayInput() {
    return await this.defaultGatewayInput.getAttribute('value');
  }

  async setDns1Input(dns1) {
    await this.dns1Input.sendKeys(dns1);
  }

  async getDns1Input() {
    return await this.dns1Input.getAttribute('value');
  }

  async setDns2Input(dns2) {
    await this.dns2Input.sendKeys(dns2);
  }

  async getDns2Input() {
    return await this.dns2Input.getAttribute('value');
  }

  async setProvUrlInput(provUrl) {
    await this.provUrlInput.sendKeys(provUrl);
  }

  async getProvUrlInput() {
    return await this.provUrlInput.getAttribute('value');
  }

  async setProvProtocolSelect(provProtocol) {
    await this.provProtocolSelect.sendKeys(provProtocol);
  }

  async getProvProtocolSelect() {
    return await this.provProtocolSelect.element(by.css('option:checked')).getText();
  }

  async provProtocolSelectLastOption() {
    await this.provProtocolSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deviceModelSelectLastOption() {
    await this.deviceModelSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deviceModelSelectOption(option) {
    await this.deviceModelSelect.sendKeys(option);
  }

  getDeviceModelSelect(): ElementFinder {
    return this.deviceModelSelect;
  }

  async getDeviceModelSelectedOption() {
    return await this.deviceModelSelect.element(by.css('option:checked')).getText();
  }

  async responsiblePersonSelectLastOption() {
    await this.responsiblePersonSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async responsiblePersonSelectOption(option) {
    await this.responsiblePersonSelect.sendKeys(option);
  }

  getResponsiblePersonSelect(): ElementFinder {
    return this.responsiblePersonSelect;
  }

  async getResponsiblePersonSelectedOption() {
    return await this.responsiblePersonSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DeviceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-device-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-device'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
