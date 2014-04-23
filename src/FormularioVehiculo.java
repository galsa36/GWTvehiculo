package es.goe.iapus.client.ui.util.pn.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.form.FormPanel;

import es.goe.iapus.client.edNueva.Vehiculo;
import es.goe.iapus.client.ui.dg.DialogoSmart;
import es.goe.iapus.client.ui.pn.PanelVehiculo;
import es.goe.iapus.client.ui.util.Utilidades;
import es.goe.iapus.client.ui.util.celltable.IFormulario;
import es.goe.iapus.client.ui.util.pn.PanelGestionVehiculo;
import es.goe.iapus.shared.AccesoIdentificado;

public class FormularioVehiculo implements IFormulario<Vehiculo> {

	
	private PanelGestionVehiculo parentPanel = null;
	private int size = 0;
	private ClickHandler listenerOK = null;
	private DialogoSmart dialogo = null;
	private PanelVehiculo pVehiculo;
	private AccesoIdentificado acceso = null;
	private boolean editar = false;
	private boolean nuevo = false;
	
	public FormularioVehiculo(PanelGestionVehiculo parentPanel){
		this.parentPanel = parentPanel;
		
		listenerOK = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogo.destroy();
			}
		};	
	}

	@Override
	public Panel getFormulario(Vehiculo object, Toolbar toolBar, int size,
			Panel pContenido, boolean e, boolean n) {
		
		
		this.size = size;
		this.editar = e;
		this.nuevo = n;
		
		Panel pForm = new Panel();
		pForm.setPaddings(10);
		pForm.setBottomToolbar(toolBar);
		pForm.setBorder(true);
		pForm.setAutoScroll(true);
		pForm.setSize("100%", "100%");

		pVehiculo = new PanelVehiculo(getInstance());
		
		FormPanel fp = new FormPanel();
		fp.setBorder(false);
		
		if(object != null) {
			pForm.setTitle(Utilidades.etiquetas.editar() + " " + Utilidades.etiquetas.vehiculo());
			pForm.add(pVehiculo.editarRegistroVehiculo(object, pContenido, !e));
			
		}else{
			pForm.setTitle(Utilidades.etiquetas.nuevoRegistro());
			pForm.add(pVehiculo.nuevoRegistro(pContenido, size+1));
		}
		
		return pForm;
		}

	@Override
	public Vehiculo getED() {
		// TODO Auto-generated method stub
		return pVehiculo.getVehiculo();
	}

	@Override
	public boolean isValid(String[] mensaje) {
		boolean isValid = true;
		if(pVehiculo.tfMatricula.getValueAsString().isEmpty()) {
			isValid = false;
			mensaje[0] += Utilidades.etiquetas.matricula();
		}
		
		return isValid;
	}

	@Override
	public boolean verificarSiExiste(String[] string) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private PanelGestionVehiculo getInstance() {
		return parentPanel;
	}

}
