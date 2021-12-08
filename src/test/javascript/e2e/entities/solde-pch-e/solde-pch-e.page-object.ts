import { element, by, ElementFinder } from 'protractor';

export class SoldePchEComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-solde-pch-e div table .btn-danger'));
  title = element.all(by.css('jhi-solde-pch-e div h2#page-heading span')).first();
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

export class SoldePchEUpdatePage {
  pageTitle = element(by.id('jhi-solde-pch-e-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  dateInput = element(by.id('field_date'));
  isActifInput = element(by.id('field_isActif'));
  isDernierInput = element(by.id('field_isDernier'));
  anneeInput = element(by.id('field_annee'));
  moisInput = element(by.id('field_mois'));
  consoMontantPchECotisationsInput = element(by.id('field_consoMontantPchECotisations'));
  consoMontantPchESalaireInput = element(by.id('field_consoMontantPchESalaire'));
  soldeMontantPchEInput = element(by.id('field_soldeMontantPchE'));
  consoHeurePchEInput = element(by.id('field_consoHeurePchE'));
  soldeHeurePchEInput = element(by.id('field_soldeHeurePchE'));

  droitsStrategiePchESelect = element(by.id('field_droitsStrategiePchE'));

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

  async setMoisInput(mois: string): Promise<void> {
    await this.moisInput.sendKeys(mois);
  }

  async getMoisInput(): Promise<string> {
    return await this.moisInput.getAttribute('value');
  }

  async setConsoMontantPchECotisationsInput(consoMontantPchECotisations: string): Promise<void> {
    await this.consoMontantPchECotisationsInput.sendKeys(consoMontantPchECotisations);
  }

  async getConsoMontantPchECotisationsInput(): Promise<string> {
    return await this.consoMontantPchECotisationsInput.getAttribute('value');
  }

  async setConsoMontantPchESalaireInput(consoMontantPchESalaire: string): Promise<void> {
    await this.consoMontantPchESalaireInput.sendKeys(consoMontantPchESalaire);
  }

  async getConsoMontantPchESalaireInput(): Promise<string> {
    return await this.consoMontantPchESalaireInput.getAttribute('value');
  }

  async setSoldeMontantPchEInput(soldeMontantPchE: string): Promise<void> {
    await this.soldeMontantPchEInput.sendKeys(soldeMontantPchE);
  }

  async getSoldeMontantPchEInput(): Promise<string> {
    return await this.soldeMontantPchEInput.getAttribute('value');
  }

  async setConsoHeurePchEInput(consoHeurePchE: string): Promise<void> {
    await this.consoHeurePchEInput.sendKeys(consoHeurePchE);
  }

  async getConsoHeurePchEInput(): Promise<string> {
    return await this.consoHeurePchEInput.getAttribute('value');
  }

  async setSoldeHeurePchEInput(soldeHeurePchE: string): Promise<void> {
    await this.soldeHeurePchEInput.sendKeys(soldeHeurePchE);
  }

  async getSoldeHeurePchEInput(): Promise<string> {
    return await this.soldeHeurePchEInput.getAttribute('value');
  }

  async droitsStrategiePchESelectLastOption(): Promise<void> {
    await this.droitsStrategiePchESelect.all(by.tagName('option')).last().click();
  }

  async droitsStrategiePchESelectOption(option: string): Promise<void> {
    await this.droitsStrategiePchESelect.sendKeys(option);
  }

  getDroitsStrategiePchESelect(): ElementFinder {
    return this.droitsStrategiePchESelect;
  }

  async getDroitsStrategiePchESelectedOption(): Promise<string> {
    return await this.droitsStrategiePchESelect.element(by.css('option:checked')).getText();
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

export class SoldePchEDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-soldePchE-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-soldePchE'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
