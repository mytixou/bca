import { element, by, ElementFinder } from 'protractor';

export class EnfantComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-enfant div table .btn-danger'));
  title = element.all(by.css('jhi-enfant div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EnfantUpdatePage {
  pageTitle = element(by.id('jhi-enfant-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  isActifInput = element(by.id('field_isActif'));
  dateNaissanceInput = element(by.id('field_dateNaissance'));
  dateInscriptionInput = element(by.id('field_dateInscription'));
  dateResiliationInput = element(by.id('field_dateResiliation'));

  beneficiaireSelect = element(by.id('field_beneficiaire'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  async setDateNaissanceInput(dateNaissance: string): Promise<void> {
    await this.dateNaissanceInput.sendKeys(dateNaissance);
  }

  async getDateNaissanceInput(): Promise<string> {
    return await this.dateNaissanceInput.getAttribute('value');
  }

  async setDateInscriptionInput(dateInscription: string): Promise<void> {
    await this.dateInscriptionInput.sendKeys(dateInscription);
  }

  async getDateInscriptionInput(): Promise<string> {
    return await this.dateInscriptionInput.getAttribute('value');
  }

  async setDateResiliationInput(dateResiliation: string): Promise<void> {
    await this.dateResiliationInput.sendKeys(dateResiliation);
  }

  async getDateResiliationInput(): Promise<string> {
    return await this.dateResiliationInput.getAttribute('value');
  }

  async beneficiaireSelectLastOption(): Promise<void> {
    await this.beneficiaireSelect.all(by.tagName('option')).last().click();
  }

  async beneficiaireSelectOption(option: string): Promise<void> {
    await this.beneficiaireSelect.sendKeys(option);
  }

  getBeneficiaireSelect(): ElementFinder {
    return this.beneficiaireSelect;
  }

  async getBeneficiaireSelectedOption(): Promise<string> {
    return await this.beneficiaireSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EnfantDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-enfant-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-enfant'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
