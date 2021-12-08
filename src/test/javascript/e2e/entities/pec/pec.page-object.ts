import { element, by, ElementFinder } from 'protractor';

export class PecComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-pec div table .btn-danger'));
  title = element.all(by.css('jhi-pec div h2#page-heading span')).first();
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

export class PecUpdatePage {
  pageTitle = element(by.id('jhi-pec-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  idProduitInput = element(by.id('field_idProduit'));
  produitSelect = element(by.id('field_produit'));
  isPlusInput = element(by.id('field_isPlus'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  isUpdatedInput = element(by.id('field_isUpdated'));
  dateModifiedInput = element(by.id('field_dateModified'));
  isActifInput = element(by.id('field_isActif'));
  pecDetailsInput = element(by.id('field_pecDetails'));

  soldeCiSelect = element(by.id('field_soldeCi'));
  soldeApaSelect = element(by.id('field_soldeApa'));
  soldePchSelect = element(by.id('field_soldePch'));
  soldePchESelect = element(by.id('field_soldePchE'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setIdProduitInput(idProduit: string): Promise<void> {
    await this.idProduitInput.sendKeys(idProduit);
  }

  async getIdProduitInput(): Promise<string> {
    return await this.idProduitInput.getAttribute('value');
  }

  async setProduitSelect(produit: string): Promise<void> {
    await this.produitSelect.sendKeys(produit);
  }

  async getProduitSelect(): Promise<string> {
    return await this.produitSelect.element(by.css('option:checked')).getText();
  }

  async produitSelectLastOption(): Promise<void> {
    await this.produitSelect.all(by.tagName('option')).last().click();
  }

  getIsPlusInput(): ElementFinder {
    return this.isPlusInput;
  }

  async setDateCreatedInput(dateCreated: string): Promise<void> {
    await this.dateCreatedInput.sendKeys(dateCreated);
  }

  async getDateCreatedInput(): Promise<string> {
    return await this.dateCreatedInput.getAttribute('value');
  }

  getIsUpdatedInput(): ElementFinder {
    return this.isUpdatedInput;
  }

  async setDateModifiedInput(dateModified: string): Promise<void> {
    await this.dateModifiedInput.sendKeys(dateModified);
  }

  async getDateModifiedInput(): Promise<string> {
    return await this.dateModifiedInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  async setPecDetailsInput(pecDetails: string): Promise<void> {
    await this.pecDetailsInput.sendKeys(pecDetails);
  }

  async getPecDetailsInput(): Promise<string> {
    return await this.pecDetailsInput.getAttribute('value');
  }

  async soldeCiSelectLastOption(): Promise<void> {
    await this.soldeCiSelect.all(by.tagName('option')).last().click();
  }

  async soldeCiSelectOption(option: string): Promise<void> {
    await this.soldeCiSelect.sendKeys(option);
  }

  getSoldeCiSelect(): ElementFinder {
    return this.soldeCiSelect;
  }

  async getSoldeCiSelectedOption(): Promise<string> {
    return await this.soldeCiSelect.element(by.css('option:checked')).getText();
  }

  async soldeApaSelectLastOption(): Promise<void> {
    await this.soldeApaSelect.all(by.tagName('option')).last().click();
  }

  async soldeApaSelectOption(option: string): Promise<void> {
    await this.soldeApaSelect.sendKeys(option);
  }

  getSoldeApaSelect(): ElementFinder {
    return this.soldeApaSelect;
  }

  async getSoldeApaSelectedOption(): Promise<string> {
    return await this.soldeApaSelect.element(by.css('option:checked')).getText();
  }

  async soldePchSelectLastOption(): Promise<void> {
    await this.soldePchSelect.all(by.tagName('option')).last().click();
  }

  async soldePchSelectOption(option: string): Promise<void> {
    await this.soldePchSelect.sendKeys(option);
  }

  getSoldePchSelect(): ElementFinder {
    return this.soldePchSelect;
  }

  async getSoldePchSelectedOption(): Promise<string> {
    return await this.soldePchSelect.element(by.css('option:checked')).getText();
  }

  async soldePchESelectLastOption(): Promise<void> {
    await this.soldePchESelect.all(by.tagName('option')).last().click();
  }

  async soldePchESelectOption(option: string): Promise<void> {
    await this.soldePchESelect.sendKeys(option);
  }

  getSoldePchESelect(): ElementFinder {
    return this.soldePchESelect;
  }

  async getSoldePchESelectedOption(): Promise<string> {
    return await this.soldePchESelect.element(by.css('option:checked')).getText();
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

export class PecDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-pec-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-pec'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
