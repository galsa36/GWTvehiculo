package es.goe.iapus.client.ui.util.pn.celltable;

import java.util.Comparator;
import java.util.Vector;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.view.client.ProvidesKey;
import com.gwtext.client.widgets.CycleButton;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.layout.VerticalLayout;

import es.goe.iapus.client.edNueva.Vehiculo;
import es.goe.iapus.client.ui.util.Utilidades;
import es.goe.iapus.client.ui.util.celltable.AlphanumComparator;
import es.goe.iapus.client.ui.util.celltable.FilteredListDataProvider;
import es.goe.iapus.client.ui.util.celltable.ICellTable;
import es.goe.iapus.client.ui.util.celltable.TextBoxAdvanced;

public class CellTableVehiculo implements ICellTable<Vehiculo> {
	
	private TextBoxAdvanced tfId = null,
			tfBastidor = null,
			tfMatriucla = null,
//			tfFechaMatriculacion = null,
			tfColor = null,
			tfTipo = null,
			tfModelo = null,
			tfMarca = null,
			tfAnio = null;
	private DateField dfFechaMatriculacion = null;
	private CycleButton tipoFiltro = null;
	
	private int[] typeFilter = null;
	

	private Vector<String> filterString;
	
	@Override
	public void initKeyProvider(ProvidesKey<Vehiculo> KEY_PROVIDER) {
		KEY_PROVIDER = new ProvidesKey<Vehiculo>() {
			@Override
			public Object getKey(Vehiculo item) {
				return (item == null) ? null : item.getIdVehiculo();
			}
			
		};

	}

	@Override
	public boolean compareData(Vehiculo value, Vector<String> filter,
			boolean avanzado) {
		boolean isFilter = false;
		if(!avanzado) {
			return String.valueOf(value.getIdVehiculo()).toLowerCase().contains(filter.get(0)) || 
					value.getMarca().toLowerCase().contains(filter.get(0)) ||
					value.getModelo().toLowerCase().contains(filter.get(0)) ||
					value.getMatricula().toLowerCase().contains(filter.get(0));
		} else {
			for(int i = 0; i < filter.size(); ++i) {
				if(filter.get(0) != null) {
					switch(i) {
					case 0:
						isFilter = FilteredListDataProvider.filterInt(value.getIdVehiculo(), filter.get(i), isFilter);
						break;
					case 1:
						isFilter = FilteredListDataProvider.filterString(value.getNumeroBastidor(), filter.get(i), isFilter);
						break;
					case 2:
						isFilter = FilteredListDataProvider.filterString(value.getMatricula(), filter.get(i), isFilter);
						break;
					case 3:
						isFilter = FilteredListDataProvider.filterDate(value.getFechaMatriculacion(), filter.get(i), typeFilter[0], isFilter);
						break;
					case 4:
						isFilter = FilteredListDataProvider.filterString(value.getColor(), filter.get(i), isFilter);
						break;
					case 5:
						isFilter = FilteredListDataProvider.filterString(value.getTipoVehiculo(), filter.get(i), isFilter);
						break;
					case 6:
						isFilter = FilteredListDataProvider.filterString(value.getMarca(), filter.get(i), isFilter);
						break;
					case 7:
						isFilter = FilteredListDataProvider.filterString(value.getModelo(), filter.get(i), isFilter);
						break;
					case 8:
						isFilter = FilteredListDataProvider.filterString(value.getAnioFabricacion(), filter.get(i), isFilter);
						break;
					}
				}
			}
		}
		return isFilter;
	}

	@Override
	public void initData(FilteredListDataProvider<Vehiculo> dataProvider) {
		// TODO Cargar datos
		
	}

	@Override
	public void initColumns(CellTable<Vehiculo> cellTable,
			FilteredListDataProvider<Vehiculo> dataProvider) {
		TextColumn<Vehiculo> idCol = new TextColumn<Vehiculo>() {

			@Override
			public String getValue(Vehiculo object) {
				return String.valueOf(object.getIdVehiculo());
			}
			
		};
		idCol.setSortable(true);
		cellTable.addColumn(idCol, "ID");
		
		ListHandler<Vehiculo> sortHandlerId = new ListHandler<Vehiculo>(dataProvider.getList());
		sortHandlerId.setComparator(idCol, new Comparator<Vehiculo>(){

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				AlphanumComparator cmp = new AlphanumComparator();
				if(o1 == o2) {
					return (o2 != null) ? cmp.compare(o1.getMatricula(),o2.getMatricula()) : 1;
				}
				if(o1 != null) {
					return (o2 != null) ? cmp.compare(String.valueOf(o1.getIdVehiculo()),String.valueOf(o2.getIdVehiculo())) : 1;
				}
				return -1;
			}
			
		});
		cellTable.addColumnSortHandler(sortHandlerId);
		cellTable.getColumnSortList().push(idCol);
		
		TextColumn<Vehiculo> marcaCol = new TextColumn<Vehiculo>() {

			@Override
			public String getValue(Vehiculo object) {
				return (object == null) ? null : object.getMarca();
			}
			
		};
		marcaCol.setSortable(true);
		cellTable.addColumn(marcaCol, Utilidades.etiquetas.marca());

		ListHandler<Vehiculo> sortHandlerMarca = new ListHandler<Vehiculo>(dataProvider.getList());
		sortHandlerMarca.setComparator(marcaCol, new Comparator<Vehiculo>(){

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				AlphanumComparator cmp = new AlphanumComparator();
				if(o1 == o2) {
					return (o2 != null) ? cmp.compare(o1.getMatricula(),o2.getMatricula()) : 1;
				}
				if(o1 != null) {
					return (o2 != null) ? cmp.compare(o1.getMarca(),o2.getMarca()) : 1;
				}
				return -1;
			}
			
		});
		cellTable.addColumnSortHandler(sortHandlerMarca);
		cellTable.getColumnSortList().push(marcaCol);
		
		TextColumn<Vehiculo> modeloCol = new TextColumn<Vehiculo>() {

			@Override
			public String getValue(Vehiculo object) {
				return (object == null) ? null : object.getModelo();
			}
			
		};
		modeloCol.setSortable(true);
		cellTable.addColumn(modeloCol, Utilidades.etiquetas.modelo());

		ListHandler<Vehiculo> sortHandlerModelo = new ListHandler<Vehiculo>(dataProvider.getList());
		sortHandlerMarca.setComparator(modeloCol, new Comparator<Vehiculo>(){

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				AlphanumComparator cmp = new AlphanumComparator();
				if(o1 == o2) {
					return (o2 != null) ? cmp.compare(o1.getMatricula(),o2.getMatricula()) : 1;
				}
				if(o1 != null) {
					return (o2 != null) ? cmp.compare(o1.getModelo(),o2.getModelo()) : 1;
				}
				return -1;
			}
			
		});
		cellTable.addColumnSortHandler(sortHandlerModelo);
		cellTable.getColumnSortList().push(modeloCol);
		
		TextColumn<Vehiculo> matriculaCol = new TextColumn<Vehiculo>() {

			@Override
			public String getValue(Vehiculo object) {
				return (object == null) ? null : object.getMarca();
			}
			
		};
		matriculaCol.setSortable(true);
		cellTable.addColumn(matriculaCol, Utilidades.etiquetas.matricula());

		ListHandler<Vehiculo> sortHandlerMatricula = new ListHandler<Vehiculo>(dataProvider.getList());
		sortHandlerMarca.setComparator(marcaCol, new Comparator<Vehiculo>(){

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				AlphanumComparator cmp = new AlphanumComparator();
				if(o1 == o2) {
					return (o2 != null) ? cmp.compare(o1.getMarca(),o2.getMarca()) : 1;
				}
				if(o1 != null) {
					return (o2 != null) ? cmp.compare(o1.getMatricula(),o2.getMatricula()) : 1;
				}
				return -1;
			}
			
		});
		cellTable.addColumnSortHandler(sortHandlerMatricula);
		cellTable.getColumnSortList().push(matriculaCol);
		
		
	
		

	}

	@Override
	public Panel getFiltroAvanzado(
			FilteredListDataProvider<Vehiculo> dataProvider) {
		FlexTable ftFormulario = new FlexTable();
		
		initFields(dataProvider);
		
		FilteredListDataProvider.setWidget(ftFormulario, 0, 0, tfId);
		FilteredListDataProvider.setWidget(ftFormulario, 1, 0, tfBastidor);
		FilteredListDataProvider.setWidget(ftFormulario, 2, 0, tfMatriucla);
		FilteredListDataProvider.setDateWidget(ftFormulario, 3, 0, dfFechaMatriculacion, tipoFiltro);
		FilteredListDataProvider.setWidget(ftFormulario, 4, 0, tfColor);
		FilteredListDataProvider.setWidget(ftFormulario, 5, 0, tfTipo);
		FilteredListDataProvider.setWidget(ftFormulario, 6, 0, tfMarca);
		FilteredListDataProvider.setWidget(ftFormulario, 7, 0, tfModelo);
		FilteredListDataProvider.setWidget(ftFormulario, 8, 0, tfAnio);
		
		FieldSet fsDatos = new FieldSet(Utilidades.etiquetas.datosVehiculo());
		fsDatos.setCollapsible(true);
		fsDatos.add(ftFormulario);
		
		Panel pFlex = new Panel();
		
		pFlex.setPaddings(10);
		pFlex.setLayout(new VerticalLayout());
		pFlex.setCollapsible(false);
		pFlex.setCollapsed(false);
		pFlex.setAutoScroll(true);
		pFlex.setBorder(false);
		
		pFlex.add(fsDatos);
		
		return pFlex;
	}

	private void initFields(final FilteredListDataProvider<Vehiculo> dataProvider) {
		filterString = new Vector<String>();
		filterString.setSize(9);
		
		tfId = new TextBoxAdvanced(Utilidades.etiquetas.identificador(), "120px", "0");
		FilteredListDataProvider.setValueChangeHandler(tfId, dataProvider, filterString);
		
		tfBastidor = new TextBoxAdvanced(Utilidades.etiquetas.numBastidor(), "120px", "1");
		FilteredListDataProvider.setValueChangeHandler(tfBastidor, dataProvider, filterString);
		
		tfMatriucla = new TextBoxAdvanced(Utilidades.etiquetas.nombre(), "120px", "2");
		FilteredListDataProvider.setValueChangeHandler(tfMatriucla, dataProvider, filterString);
		
		typeFilter = new int[1];
		tipoFiltro = new CycleButton();
		dfFechaMatriculacion = new DateField(Utilidades.etiquetas.fechaMatriculacion(), "4", 90);
		FilteredListDataProvider.setValueChangeHandler(dfFechaMatriculacion, dataProvider, filterString, typeFilter, tipoFiltro);
		
		tfColor = new TextBoxAdvanced(Utilidades.etiquetas.color(), "120px", "4");
		FilteredListDataProvider.setValueChangeHandler(tfColor, dataProvider, filterString);
		
		tfTipo = new TextBoxAdvanced(Utilidades.etiquetas.tipoVehiculo(), "120px", "5");
		FilteredListDataProvider.setValueChangeHandler(tfTipo, dataProvider, filterString);
		
		tfMarca = new TextBoxAdvanced(Utilidades.etiquetas.marca(), "120px", "6");
		FilteredListDataProvider.setValueChangeHandler(tfMarca, dataProvider, filterString);
		
		tfModelo = new TextBoxAdvanced(Utilidades.etiquetas.modelo(), "120px", "7");
		FilteredListDataProvider.setValueChangeHandler(tfModelo, dataProvider, filterString);
		
		tfAnio = new TextBoxAdvanced(Utilidades.etiquetas.anioFabricacion(), "120px", "8");
		FilteredListDataProvider.setValueChangeHandler(tfAnio, dataProvider, filterString);
	}
	
	@Override
	public void resetFilter() {
		tfId.setValue("0");
		tfBastidor.setValue("");
		tfMatriucla.setValue("");
		dfFechaMatriculacion.reset();
		tfColor.setValue("");
		tfTipo.setValue("");
		tfMarca.setValue("");
		tfModelo.setValue("");
		tfAnio.setValue("");

	}

}
