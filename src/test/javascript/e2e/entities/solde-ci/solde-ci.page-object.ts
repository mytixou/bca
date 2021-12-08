import { element, by, ElementFinder } from 'protractor';

export class SoldeCiComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-solde-ci div table .btn-danger'));
  title = element.all(by.css('jhi-solde-ci div h2#page-heading span')).first();
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

export class SoldeCiUpdatePage {
  pageTitle = element(by.id('jhi-solde-ci-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  dateInput = element(by.id('field_date'));
  isActifInput = element(by.id('field_isActif'));
  isDernierInput = element(by.id('field_isDernier'));
  anneeInput = element(by.id('field_annee'));
  consoMontantCiInput = element(by.id('field_consoMontantCi'));
  consoCiRecInput = element(by.id('field_consoCiRec'));
  soldeMontantCiInput = element(by.id('field_soldeMontantCi'));
  soldeMontantCiRecInput = element(by.id('field_soldeMontantCiRec'));

  droitsStrategieCiSelect = element(by.id('field_droitsStrategieCi'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  getIsDernierInput(): ElementFinder {
    return this.isDernierInput;
  }

  async setAnneeInput(annee: string): Promise<void> {
    await this.anneeInput.sendKeys(annee);
  }

  async getAnneeInput(): Promise<string> {
    return await this.anneeInput.getAttribute('value');
  }

  async setConsoMontantCiInput(consoMontantCi: string): Promise<void> {
    await this.consoMontantCiInput.sendKeys(consoMontantCi);
  }

  async getConsoMontantCiInput(): Promise<string> {
    return await this.consoMontantCiInput.getAttribute('value');
  }

  async setConsoCiRecInput(consoCiRec: string): Promise<void> {
    await this.consoCiRecInput.sendKeys(consoCiRec);
  }

  async getConsoCiRecInput(): Promise<string> {
    return await this.consoCiRecInput.getAttribute('value');
  }

  async setSoldeMontantCiInput(soldeMontantCi: string): Promise<void> {
    await this.soldeMontantCiInput.sendKeys(soldeMontantCi);
  }

  async getSoldeMontantCiInput(): Promise<string> {
    return await this.soldeMontantCiInput.getAttribute('value');
  }

  async setSoldeMontantCiRecInput(soldeMontantCiRec: string): Promise<void> {
    await this.soldeMontantCiRecInput.sendKeys(soldeMontantCiRec);
  }

  async getSoldeMontantCiRecInput(): Promise<string> {
    return await this.soldeMontantCiRecInput.getAttribute('value');
  }

  async droitsStrategieCiSelectLastOption(): Promise<void> {
    await this.droitsStrategieCiSelect.all(by.tagName('option')).last().click();
  }

  async droitsStrategieCiSelectOption(option: string): Promise<void> {
    await this.droitsStrategieCiSelect.sendKeys(option);
  }

  getDroitsStrategieCiSelect(): ElementFinder {
    return this.droitsStrategieCiSelect;
  }

  async getDroitsStrategieCiSelectedOption(): Promise<string> {
    return await this.droitsStrategieCiSelect.element(by.css('option:checked')).getText();
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

export class SoldeCiDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-soldeCi-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-soldeCi'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
