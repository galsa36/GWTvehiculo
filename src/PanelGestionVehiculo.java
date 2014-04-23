package es.goe.iapus.client.ui.util.pn;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtext.client.widgets.Panel;

import es.goe.iapus.client.cte.Constantes;
import es.goe.iapus.client.edNueva.Persona;
import es.goe.iapus.client.edNueva.Vehiculo;
import es.goe.iapus.client.ui.dg.DialogoSmart;
import es.goe.iapus.client.ui.util.CuadroDeMensaje;
import es.goe.iapus.client.ui.util.Utilidades;
import es.goe.iapus.client.ui.util.celltable.PanelGestion;
import es.goe.iapus.client.ui.util.pn.celltable.CellTableVehiculo;
import es.goe.iapus.client.ui.util.pn.form.FormularioVehiculo;
import es.goe.iapus.shared.AccesoIdentificado;

@SuppressWarnings("unchecked")
public class PanelGestionVehiculo extends PanelGestion<Vehiculo>{

	public AccesoIdentificado acceso = null;
	
	public PanelGestionVehiculo(AccesoIdentificado acces, Panel pContenido) {
		super(Utilidades.etiquetas.vehiculo(), 
				pContenido,
				"images/icAddVehiculo.png",
				15);
		this.acceso = acces;
		this.iCellTable = new CellTableVehiculo();
		this.iForm = new FormularioVehiculo(this);
		initPanel();
		
	}
	
	/**
	 * Método que se lanza cuando se pulsa el botón guardar del toolBar
	 */
	@Override
	protected void onClickBtGuardar() {
		Vehiculo item = selectionModel.getSelectedObject();
		mensaje = new String[1];
		mensaje[0] = new String("Debe completar los campos obligatorios del formulario:\n");
		if (iForm.isValid(mensaje)) {
			Vehiculo aux = iForm.getED();
			if (item != null) { // Update
				int index = dataProvider.getList().indexOf(item);
				dataProvider.getList().remove(item);
				dataProvider.getList().add(index, aux);
				pDatos.clear();
				dataProvider.resetFilter();
				dataProvider.flush();
				cellTable.setRowCount(dataProvider.getList().size(), true);
				cellTable.setRowData(0, dataProvider.getList());
				// selectionModel.clear();
			} else { // Insert
				if(iControlador != null){
					if(!iForm.verificarSiExiste(mensaje)){
						CuadroDeMensaje.show(Utilidades.etiquetas.cargando());
						iControlador.insert(aux);
						dataProvider.getList().add(aux);
						pDatos.clear();
						dataProvider.resetFilter();
						dataProvider.flush();
						cellTable.setRowCount(dataProvider.getList().size(), true);
						cellTable.setRowData(0, dataProvider.getList());
					}
					else{
						dialogo = new DialogoSmart("Error: ", "El registro ya existe en la BBDD", new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								dialogo.destroy();
								dialogo = null;
							}
						}, Constantes.ICONO_MENSAJE_ERROR);
						dialogo.show();
					}
						
				}else{
					dataProvider.getList().add(aux);
					dataProvider.resetFilter();
					dataProvider.flush();
					cellTable.setRowCount(dataProvider.getList().size(), true);
					cellTable.setRowData(0, dataProvider.getList());
					pDatos.clear();
				}

			}

		} else {
			dialogo = new DialogoSmart("Error guardando formulario", mensaje[0], new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					dialogo.destroy();
					dialogo = null;
				}
			}, Constantes.ICONO_MENSAJE_ERROR);
			dialogo.show();
		}
	}


}
