import { element, by, ElementFinder } from 'protractor';

export class SoldePchComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-solde-pch div table .btn-danger'));
  title = element.all(by.css('jhi-solde-pch div h2#page-heading span')).first();
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

export class SoldePchUpdatePage {
  pageTitle = element(by.id('jhi-solde-pch-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  dateInput = element(by.id('field_date'));
  isActifInput = element(by.id('field_isActif'));
  isDernierInput = element(by.id('field_isDernier'));
  anneeInput = element(by.id('field_annee'));
  moisInput = element(by.id('field_mois'));
  consoMontantPchCotisationsInput = element(by.id('field_consoMontantPchCotisations'));
  consoMontantPchSalaireInput = element(by.id('field_consoMontantPchSalaire'));
  soldeMontantPchInput = element(by.id('field_soldeMontantPch'));
  consoHeurePchInput = element(by.id('field_consoHeurePch'));
  soldeHeurePchInput = element(by.id('field_soldeHeurePch'));

  droitsStrategiePchSelect = element(by.id('field_droitsStrategiePch'));

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

  async setConsoMontantPchCotisationsInput(consoMontantPchCotisations: string): Promise<void> {
    await this.consoMontantPchCotisationsInput.sendKeys(consoMontantPchCotisations);
  }

  async getConsoMontantPchCotisationsInput(): Promise<string> {
    return await this.consoMontantPchCotisationsInput.getAttribute('value');
  }

  async setConsoMontantPchSalaireInput(consoMontantPchSalaire: string): Promise<void> {
    await this.consoMontantPchSalaireInput.sendKeys(consoMontantPchSalaire);
  }

  async getConsoMontantPchSalaireInput(): Promise<string> {
    return await this.consoMontantPchSalaireInput.getAttribute('value');
  }

  async setSoldeMontantPchInput(soldeMontantPch: string): Promise<void> {
    await this.soldeMontantPchInput.sendKeys(soldeMontantPch);
  }

  async getSoldeMontantPchInput(): Promise<string> {
    return await this.soldeMontantPchInput.getAttribute('value');
  }

  async setConsoHeurePchInput(consoHeurePch: string): Promise<void> {
    await this.consoHeurePchInput.sendKeys(consoHeurePch);
  }

  async getConsoHeurePchInput(): Promise<string> {
    return await this.consoHeurePchInput.getAttribute('value');
  }

  async setSoldeHeurePchInput(soldeHeurePch: string): Promise<void> {
    await this.soldeHeurePchInput.sendKeys(soldeHeurePch);
  }

  async getSoldeHeurePchInput(): Promise<string> {
    return await this.soldeHeurePchInput.getAttribute('value');
  }

  async droitsStrategiePchSelectLastOption(): Promise<void> {
    await this.droitsStrategiePchSelect.all(by.tagName('option')).last().click();
  }

  async droitsStrategiePchSelectOption(option: string): Promise<void> {
    await this.droitsStrategiePchSelect.sendKeys(option);
  }

  getDroitsStrategiePchSelect(): ElementFinder {
    return this.droitsStrategiePchSelect;
  }

  async getDroitsStrategiePchSelectedOption(): Promise<string> {
    return await this.droitsStrategiePchSelect.element(by.css('option:checked')).getText();
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

export class SoldePchDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-soldePch-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-soldePch'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
