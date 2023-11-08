package com.dlalaweb.phones.details;

import java.util.Observable;
import java.util.Observer;

import com.dlalacore.dlala.entities.Phone;
import com.dlalaweb.phones.details.onglet.Phone.DetailsPhonePresenter;
import com.dlalaweb.phones.details.onglet.reparations.ReparationsController;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Window;

public class DetailsController extends Observable implements Observer {
	private DetailsView						view;
	private DetailsPhonePresenter	detailsPhone;
	// private ReparationsPresenter historique;
	private ReparationsController	reparationsController;
	private Phone									phoneSelected;

	public DetailsController() {
		this.view = new DetailsView();
		detailsPhone = new DetailsPhonePresenter();
		detailsPhone.addObserver(this);
		setListeners();
	}

	public DetailsController(Phone phoneSelected) {
		this.phoneSelected = phoneSelected;
		this.view = new DetailsView();
		detailsPhone = new DetailsPhonePresenter(phoneSelected);
		detailsPhone.addObserver(this);
		this.getView().getTabSheetContent().addTab(detailsPhone.getView(), "Détails du téléphone", VaadinIcons.PHONE)
		    .setClosable(true);
		if (!detailsPhone.getHistoreparations().isEmpty()) {
			detailsPhone.ifFicheExiste();

			this.getView().getTabSheetContent().setSelectedTab(0);
		}
		setListeners();

	}

	private void setListeners() {
		this.view.getWinContent().addCloseListener(e -> onWindowsClosed());

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof DetailsPhonePresenter) {
			// if (arg.equals("close window")) {
			// setChanged();
			// notifyObservers("close window");
			//
			//
			// }
			if (arg.equals("Historique éxistante")) {

				this.reparationsController = new ReparationsController(detailsPhone.getHistoreparations(), this.phoneSelected);

				reparationsController.addObserver(this);
				this.getView().getTabSheetContent()
				    .addTab(reparationsController.getReparationsPresenter().getView().getContent(), "Historique de réparation",
				        VaadinIcons.BOOKMARK)
				    .setClosable(false);
				this.getView().getTabSheetContent().setSelectedTab(0);

			}

			if (arg.equals("Ajouter réparation")) {
				System.out.println("ajouter reéparation");
			}
			if (arg.equals("Effacer téléphone")) {
				System.out.println("effacer phone");
			}

		}
		// if(o instanceof ReparationsPresenter) {
		// if(arg instanceof ReparationsPresenter) {
		// Fiche fiche = (Fiche) arg;
		// // lancer le prenseter raparation details
		// }
		// }

	}

	public DetailsView getView() {
		return view;
	}

	public Window getOngletPhone() {
		this.getView().getTabSheetContent().addTab(detailsPhone.getView(), "Détails du téléphone", VaadinIcons.PHONE)
		    .setClosable(true);
		return getView().getWinContent();
	}

	public DetailsPhonePresenter getDetailsPhone() {
		return detailsPhone;
	}

	// public ReparationsPresenter getHistorique() {
	// return historique;
	// }

	private void onWindowsClosed() {

		this.setChanged();
		this.notifyObservers("close window");
	}

}
