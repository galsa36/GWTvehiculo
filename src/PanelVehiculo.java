package es.goe.iapus.client.ui.pn;

import java.util.Date;

import com.goe.gwt.core.client.GWT;
import com.goe.gwt.event.dom.client.ClickEvent;
import com.goe.gwt.event.dom.client.ClickHandler;
import com.goe.gwt.user.client.ui.FileUpload;
import com.goe.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.goe.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.goe.gwt.user.client.ui.HasHorizontalAlignment;
import com.goe.gwt.user.client.ui.Label;
import com.goe.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.ObjectFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.DatePicker;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DatePickerListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridCellListener;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;

import es.goe.iapus.client.Etiquetas;
import es.goe.iapus.client.cte.Constantes;
import es.goe.iapus.client.ed.Impuesto;
import es.goe.iapus.client.ed.Impuestos;
import es.goe.iapus.client.ed.Itv;
import es.goe.iapus.client.ed.Itvs;
import es.goe.iapus.client.edNueva.Direccion;
import es.goe.iapus.client.edNueva.DireccionAsociacion;
import es.goe.iapus.client.edNueva.DireccionesAsociaciones;
import es.goe.iapus.client.edNueva.Documentacion;
import es.goe.iapus.client.edNueva.Documentaciones;
import es.goe.iapus.client.edNueva.Persona;
import es.goe.iapus.client.edNueva.PersonaAsociacion;
import es.goe.iapus.client.edNueva.PersonasAsociaciones;
import es.goe.iapus.client.edNueva.Seguro;
import es.goe.iapus.client.edNueva.Seguros;
import es.goe.iapus.client.edNueva.Vehiculo;
import es.goe.iapus.client.ui.dg.DialogoInsertarRegistroImpuesto;
import es.goe.iapus.client.ui.dg.DialogoInsertarRegistroItv;
import es.goe.iapus.client.ui.dg.DialogoMostrarRegistroImpuesto;
import es.goe.iapus.client.ui.dg.DialogoMostrarRegistroItv;
import es.goe.iapus.client.ui.dg.DialogoRegistroDocumentaciones;
import es.goe.iapus.client.ui.dg.DialogoRegistroFechas;
import es.goe.iapus.client.ui.dg.DialogoSeguro;
import es.goe.iapus.client.ui.dg.DialogoSmart;
import es.goe.iapus.client.ui.util.BotonEnlace;
import es.goe.iapus.client.ui.util.CampoLista;
import es.goe.iapus.client.ui.util.FieldSetObservaciones;
import es.goe.iapus.client.ui.util.HTMLD;
import es.goe.iapus.client.ui.util.SimpleDateFormat;
import es.goe.iapus.client.ui.util.Utilidades;
import es.goe.iapus.client.ui.util.celltable.DialogoGestion;
import es.goe.iapus.client.ui.util.celltable.PanelGestion;
import es.goe.iapus.client.ui.util.pn.PanelGestionDireccion;
import es.goe.iapus.client.ui.util.pn.PanelGestionPersonas;
import es.goe.iapus.client.ui.util.pn.PanelGestionVehiculo;
import es.goe.iapus.client.ui.util.pn.celltable.CellTableDireccion;
import es.goe.iapus.client.ui.util.pn.celltable.CellTablePersona;
import es.goe.iapus.shared.AccesoIdentificado;

/**
 * Llamamos a esta clase para mostrar una ventana donde el usuario puede introducir los datos de la persona.
 * Implementamos también la posibilidad de editar registros desde esta misma clase
 * 
 * Lleva asociados el control de eventos del Escape y del Ctrl+s para guardar el registro
 */

public class PanelVehiculo extends Panel{
	
	private Vehiculo vehiculo = null;
	private Panel parentPanel = null;
	
	
	private Etiquetas etiquetas = (Etiquetas) GWT.create(Etiquetas.class);
	private com.goe.gwt.user.client.ui.FormPanel formUploadImagen = null;
	private com.goe.gwt.user.client.ui.FormPanel formUploadFichero = null;
	private static final String UPLOAD_ACTION_URL_IMAGEN = GWT.getModuleBaseURL() + "ServletSubirImagen";
	private static final String UPLOAD_ACTION_URL_FICHERO = GWT.getModuleBaseURL() + "ServletSubirFichero";
	private FileUpload uploadImagen = null;
	private FileUpload uploadFichero = null;
	
	private boolean fechaComprobada = false;	
	private boolean mantenerFecha = true;
	private int posicion;
	
//	TextField Vehiculo
	private TextField tfNumBastidor = null;
	public TextField tfMatricula = null;
	private DateField dfFechaMatriculacion = null;
	private NumberField nfAnioFabricacion = null;
	private TextField tfColor = null;
	private CampoLista clCombustible = null;
	private NumberField nfCilindrada = null;
	private NumberField nfPotencia = null;	
	private TextField tfJefaturaProvTrafico = null;
	private DateField dfFechaExpJefatura = null;

	private Button btAddSeguro = null;
	private Button btAddImpuesto = null;
	private Button btAddItv = null;
	
	public DireccionesAsociaciones direcciones= null;
	private DireccionAsociacion direccion = null;
	
	private PersonasAsociaciones propietariosVehiculo = null;
	private PersonaAsociacion propietarioVehiculo = null;

	
	private Seguros seguros = null;
	private Itvs itvs = null;
	private Impuestos impuestos = null;
	
//	Grid Propietarios
	private GridPanel gridPropietarios = null;
	private RecordDef recordsPropietarios = null;
	private Store storePropietarios = null;
	private MemoryProxy proxyPropietarios = null;
	private Object[][] objfilePropietarios = null;
	private RowSelectionModel modeloSeleccionPropietarios = null;
	

//	Grid Direcciones
	public GridPanel gridDirecciones = null;
	private RecordDef recordsDirecciones = null;
	private Store storeDirecciones = null;
	private MemoryProxy proxyDirecciones = null;
	private Object[][] objDireccion = null;
	private RowSelectionModel modeloSeleccionDireccion = null;
	

//	Grid Seguros
	private GridPanel gridSeguros = null;
	private RecordDef recordsSeguros = null;
	private Store storeSeguros = null;
	private MemoryProxy proxySeguros = null;
	private Object[][] objfileSeguros = null;
	private RowSelectionModel modeloSeleccionSeguros = null;
	

//	Grid Impuestos
	private GridPanel gridImpuestos = null;
	private RecordDef recordsImpuestos = null;
	private Store storeImpuestos = null;
	private MemoryProxy proxyImpuestos = null;
	private Object[][] objfileImpuestos = null;
	private RowSelectionModel modeloSeleccionImpuestos = null;
	
//	Grid Itvs
	private GridPanel gridItvs = null;
	private RecordDef recordsItvs = null;
	private MemoryProxy proxyItvs = null;
	private Object[][] objfileItvs = null;
	private RowSelectionModel modeloSeleccionItvs = null;
	
	public Documentaciones documentaciones = null;
	private Documentacion documentacion = null;
	
//	Grid Documentaciones
	public GridPanel gridDocumentacion = null;
	private RecordDef recordsDocumentacion = null;
	private Store storeDocumentacion = null;
	private MemoryProxy proxyDocumentacion = null;
	private Object[][] objDocumentacion = null;
	private RowSelectionModel modeloSeleccionDocumentacion = null;

	
	private TabPanel tabPanel = null;
	
	
//	Campos listas
	private CampoLista cbMarcas = null;
	private CampoLista cbModelos = null;
	private CampoLista cbTipos = null;	
	

	private boolean eliminarActivo;
		
	private ClickHandler listenerOK = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	
	private DialogoSmart dialogo = null;
	private boolean dialogoVisible = false;
	
	//DIALOGO direccion
	private int ALTURA_DIALOGO_REG = 0;
	private int ANCHURA_DIALOGO_REG = 0;

	
	public boolean disable = false;
	public int sizeCellTablePersonas = 0;

	
	
	private int ALTURA_PANTALLA_REG = 0;
	private int ANCHURA_PIZQD = 0;
	private int ANCHURA_PANTALLA_REG = 1024;
	private int ALTURA_CAMPO_FS = 50;
	private int ANCHURA_REGISTRO = ANCHURA_PANTALLA_REG - 20;
	
	public AccesoIdentificado acceso = null;
	
	private FieldSetObservaciones fsObservaciones = null;
	
	
	/**
     * Constructor vacío de la clase
     */
	public PanelVehiculo() {
		super();
	}
	
	/**
     * Constructor con los parámetros básicos con un panel como argumento
     */
	public PanelVehiculo(Panel parentPanel) {
		super();
		this.parentPanel = parentPanel;
		this.acceso = ((PanelGestionVehiculo) parentPanel).acceso;
		listenerOK = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogo.destroy();
			}
		};
	}

	/**
	 * Creamos una nueva persona
	 * @param pContenido
	 * @param size
	 * @return
	 */
	public Panel nuevoRegistro(Panel pContenido, int size) {
		
		this.sizeCellTablePersonas = size; 
		setSize(pContenido);
		setPaddings(10);
		setAutoScroll(true);
		setLayout(new VerticalLayout(5));
		
		eliminarActivo = true;
		add(getFormulario());
		return this;
	}
	
	public Panel getFormulario() {
		Panel p = new Panel();
		p.setPaddings(10);
		p.setLayout(new VerticalLayout(10));
		
//		Fieldset para los datos de la inserción
		FieldSet fsDatosInsercion = new FieldSet(etiquetas.identificacionRegistro());
		fsDatosInsercion.setBorder(true);
		fsDatosInsercion.setWidth(ANCHURA_REGISTRO);
	
		Panel pRegistro1 = new Panel();
		pRegistro1.setBorder(false);
		pRegistro1.setPaddings(5);
		pRegistro1.setLayout(new ColumnLayout());

		com.smartgwt.client.widgets.Button 	btAsociarExpediente = new com.smartgwt.client.widgets.Button(etiquetas.asociarExpediente());
		btAsociarExpediente.setIcon("data-accept16.png");		
		btAsociarExpediente.setWidth(150);
		btAsociarExpediente.setZIndex(0);
	
		btAsociarExpediente.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					
				
			}
		});
		
		
		pRegistro1.add(new HTMLD(etiquetas.agente(), "K99"), new ColumnLayoutData(0.35));
		pRegistro1.add(new HTMLD(etiquetas.fecha(), sdf.format(new Date())), new ColumnLayoutData(0.38));
		pRegistro1.add(btAsociarExpediente, new ColumnLayoutData(0.27));
		
		fsDatosInsercion.add(pRegistro1);	
		
					
//		Fieldset para los datos del vehiculo
		FieldSet fsDatosVehiculo = new FieldSet(etiquetas.datosVehiculo());
		fsDatosVehiculo.setBorder(true);
		fsDatosVehiculo.setWidth(ANCHURA_REGISTRO);
		
		Panel pRegistro2 = new Panel();
		pRegistro2.setBorder(false);		
		pRegistro2.setLayout(new ColumnLayout());
		pRegistro2.setHeight(ALTURA_CAMPO_FS+5);
				
//		Numero Bastidor
		VerticalPanel vpNumBastidor = new VerticalPanel();
		vpNumBastidor.add(new HTMLD(etiquetas.numBastidor()));
		tfNumBastidor = new TextField(etiquetas.numBastidor(),"numBastidor",120,"");
		vpNumBastidor.add(tfNumBastidor);		
		
//		Matrícula		
		FormPanel fpMatricula = new FormPanel();		
		fpMatricula.setBorder(false);	
		fpMatricula.add(new HTMLD("* " + etiquetas.matricula(),true));		
		tfMatricula = new TextField();		
		tfMatricula.setWidth(120);
		tfMatricula.setId(Ext.generateId());
		tfMatricula.setHideLabel(true);
		tfMatricula.setAllowBlank(false);
	
		tfMatricula.setMaxLength(20);
		tfMatricula.setBlankText(etiquetas.campoMatriculaObligatorio());
		fpMatricula.add(tfMatricula);		
		
		
//		Fecha Matriculacion
		dfFechaMatriculacion = new DateField() ;
		dfFechaMatriculacion.setId(Ext.generateId());
		dfFechaMatriculacion.setFormat("d/m/Y");
		dfFechaMatriculacion.setHideLabel(true);
		dfFechaMatriculacion.setMaxLengthText("10");
		dfFechaMatriculacion.setAllowBlank(false);
		dfFechaMatriculacion.setMaskRe("[\\d/]+");	
		dfFechaMatriculacion.setWidth(120);
		dfFechaMatriculacion.setBlankText(etiquetas.fechaMatriculacion());

		dfFechaMatriculacion.addListener(new DatePickerListenerAdapter(){
			public void onSelect(DatePicker dataPicker, java.util.Date date){				
				dfFechaMatriculacion.setValue(new Date());
				mantenerFecha = fechaValida(date, dfFechaMatriculacion);	
				fechaComprobada = true;
			}
		});
		
		dfFechaMatriculacion.addListener(new ComboBoxListenerAdapter(){
			public void onBlur(Field field)
			{				
				if(!fechaComprobada){
					fechaValida(dfFechaMatriculacion.getValue(), dfFechaMatriculacion);
				}
				else if(!mantenerFecha) dfFechaMatriculacion.setValue("");
				fechaComprobada = false;
			}
		});
		
		FormPanel fpFechaMatriculacion = new FormPanel();		
		fpFechaMatriculacion.setBorder(false);			
		
		fpFechaMatriculacion.add(new HTMLD(etiquetas.fechaMatriculacion()));
		fpFechaMatriculacion.add(dfFechaMatriculacion); 		
		
//		Color
		VerticalPanel vpColor = new VerticalPanel();
		vpColor.add(new HTMLD(etiquetas.color()));
		tfColor = new TextField(etiquetas.color(),"color",120,"");
		vpColor.add(tfColor);
		
		pRegistro2.add(vpNumBastidor,new ColumnLayoutData(0.25));		
		pRegistro2.add(fpMatricula,new ColumnLayoutData(0.25));
		pRegistro2.add(fpFechaMatriculacion,new ColumnLayoutData(0.25));
		pRegistro2.add(vpColor,new ColumnLayoutData(0.25));
		
		Panel pRegistro3 = new Panel();
		pRegistro3.setBorder(false);		
		pRegistro3.setLayout(new ColumnLayout());
		pRegistro3.setHeight(ALTURA_CAMPO_FS+5);
		
		
//		Tipo
		cbTipos = new CampoLista(etiquetas.tipoVehiculo(), Constantes.TIPOSVEHICULOS, false, false);
		cbTipos.getCombo().addListener(new ComboBoxListenerAdapter() {    
            public void onSelect(ComboBox comboBox, Record record, int index) { 
            	cbMarcas.getCombo().setDisabled(false);
            	cbModelos.getCombo().setDisabled(true);
            	cbMarcas.setValue("");
            	cbModelos.setValue("");
            	cbMarcas.filtrarPorId(cbTipos.getCombo().getValue());            
            }  
        });  
		
//		Marca
		cbMarcas = new CampoLista(etiquetas.marca(), Constantes.MARCAS, false,true);
		cbMarcas.getCombo().addListener(new ComboBoxListenerAdapter() {    
            public void onSelect(ComboBox comboBox, Record record, int index) { 
            	cbModelos.getCombo().setDisabled(false);
            	cbModelos.setValue("");
            	cbTipos.setValue(record.getAsString("nomDep"));
            	cbModelos.filtrarPorId(cbMarcas.getCombo().getValue());
            }  
        });  
		
//		Modelo
		cbModelos = new CampoLista(etiquetas.modelo(), Constantes.MODELOS, true,true);		
		
//		Año de fabricacion
		VerticalPanel vpAnioFabricacion = new VerticalPanel();
		vpAnioFabricacion.add(new HTMLD(etiquetas.anioFabricacion()));
		nfAnioFabricacion = new NumberField(etiquetas.anioFabricacion(),"anioFabricacion",120);
		nfAnioFabricacion.setAllowDecimals(false);
		vpAnioFabricacion.add(nfAnioFabricacion);	
		
		pRegistro3.add(cbTipos,new ColumnLayoutData(0.25));
		pRegistro3.add(cbMarcas,new ColumnLayoutData(0.25));
		pRegistro3.add(cbModelos,new ColumnLayoutData(0.25));
		pRegistro3.add(vpAnioFabricacion,new ColumnLayoutData(0.25));
		
		fsDatosVehiculo.add(pRegistro2);
		fsDatosVehiculo.add(pRegistro3);
		
//		TAB PROPIETARIOS
		Panel pTabPropietarios = new Panel();
		pTabPropietarios.setTitle(etiquetas.propietario());
		pTabPropietarios.setBorder(true);
		pTabPropietarios.setPaddings(5);
		pTabPropietarios.add(getPanelGridPropietarios(propietariosVehiculo,true));
		
//		TAB DIRECCION		
		Panel pTabDirecciones = new Panel();
		pTabDirecciones.setTitle(etiquetas.direccionVehiculo());
		pTabDirecciones.setBorder(true);
		pTabDirecciones.setPaddings(5);
		pTabDirecciones.add(getPanelGridDirecciones(direcciones,true));		
		
//		TAB SEGUROS
		Panel pTabSeguros = new Panel();
		pTabSeguros.setTitle(etiquetas.seguros());
		pTabSeguros.setBorder(true);	
		pTabSeguros.add(cargarGridSeguros());
		
//		TAB IMPUESTOS
		Panel pTabImpuestos = new Panel();
		pTabImpuestos.setTitle(etiquetas.impuestos());
		pTabImpuestos.setBorder(true);	
		pTabImpuestos.add(cargarGridImpuestos());	
		
//		TAB ITV		
		Panel pTabItv = new Panel();	
		pTabItv.setBorder(true);
		pTabItv.setTitle(etiquetas.itv());
		pTabItv.add(cargarGridItv());
		
//		TAB Documentos		
		Panel pTabDoc = new Panel();	
		pTabDoc.setBorder(true);
		pTabDoc.setPaddings(5);
		pTabDoc.setTitle(etiquetas.documentacion());
		pTabDoc.add(getPanelGridDocumentaciones(documentaciones,true));		
		
//		TabPanel con los expedientes sobre los que está asociada la persona
		Panel pExpedientes = new Panel();
		pExpedientes.setTitle("Expedientes");
		pExpedientes.setBorder(true);
		pExpedientes.setPaddings(10);
		pExpedientes.setLayout(new HorizontalLayout(10));
		
//		Replicar tantas veces como expedientes tenga asociada la persona
		if(vehiculo != null){
			for (int i = 0; i<5; ++i){
				pExpedientes.add(getExpedientes(i, vehiculo.getMatricula()));
			}
		}
		
		Panel pImagenes = new Panel();
		pImagenes.setBorder(true);
		pImagenes.setPaddings(5);
		pImagenes.setTitle(etiquetas.imagen());

//		TAB OTROS DATOS 
		Panel pOtrosDatos = new Panel();		
		pOtrosDatos.setLayout(new VerticalLayout());
		pOtrosDatos.setPaddings(10);
		pOtrosDatos.setBorder(true);
		pOtrosDatos.setTitle(etiquetas.otrosDatos());
		
		Panel pRegistroOtrosDatos1 = new Panel();
		pRegistroOtrosDatos1.setWidth(ANCHURA_REGISTRO);
		pRegistroOtrosDatos1.setBorder(false);
		pRegistroOtrosDatos1.setLayout(new ColumnLayout());
		pRegistroOtrosDatos1.setHeight(ALTURA_CAMPO_FS+5);
		
//		Tipo Combustible
		clCombustible = new CampoLista(etiquetas.combustible(), Constantes.TIPOS_COMBUSTIBLE, false,false,"120px");	
		
//		Cilindrada
		VerticalPanel vpCilindrada = new VerticalPanel();
		vpCilindrada.add(new HTMLD(etiquetas.cilindrada()));
		nfCilindrada = new NumberField(etiquetas.cilindrada(),"cilindrada",120);
		nfCilindrada.setAllowDecimals(false);
		vpCilindrada.add(nfCilindrada);	
		
//		Potencia
		VerticalPanel vpPotencia = new VerticalPanel();
		vpPotencia.add(new HTMLD("KW"));
		nfPotencia = new NumberField("KW","potenciaFiscal",120);
		nfPotencia.setAllowDecimals(false);
		vpPotencia.add(nfPotencia);
		
		pRegistroOtrosDatos1.add(clCombustible,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos1.add(vpCilindrada,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos1.add(vpPotencia,new ColumnLayoutData(0.33));	

		Panel pRegistroOtrosDatos2 = new Panel();
		pRegistroOtrosDatos2.setWidth(ANCHURA_REGISTRO);
		pRegistroOtrosDatos2.setBorder(false);
		pRegistroOtrosDatos2.setLayout(new ColumnLayout());
		pRegistroOtrosDatos2.setHeight(ALTURA_CAMPO_FS+5);
		
//		Jefatura Provincial de tráfico
		VerticalPanel vpJefaturaProvTrafico = new VerticalPanel();
		vpJefaturaProvTrafico.add(new HTMLD(etiquetas.jefaturaProvTrafico()));
		tfJefaturaProvTrafico = new TextField(etiquetas.jefaturaProvTrafico(),"jefaturaProvTrafico",120,"");
		vpJefaturaProvTrafico.add(tfJefaturaProvTrafico);	
		
//		Fecha expedición jefatura
		dfFechaExpJefatura = new DateField() ;
		dfFechaExpJefatura.setId(Ext.generateId());
		dfFechaExpJefatura.setFormat("d/m/Y");
		dfFechaExpJefatura.setHideLabel(true);
		dfFechaExpJefatura.setMaxLengthText("10");
		dfFechaExpJefatura.setAllowBlank(false);
		dfFechaExpJefatura.setMaskRe("[\\d/]+");	
		dfFechaExpJefatura.setWidth(120);
		dfFechaExpJefatura.setBlankText(etiquetas.fechaExpedicionJefatura());

		dfFechaExpJefatura.addListener(new DatePickerListenerAdapter(){
			public void onSelect(DatePicker dataPicker, java.util.Date date){				
				dfFechaExpJefatura.setValue(new Date());
				mantenerFecha = fechaValida(date, dfFechaExpJefatura);	
				fechaComprobada = true;
			}
		});
		
		dfFechaExpJefatura.addListener(new ComboBoxListenerAdapter(){
			public void onBlur(Field field)
			{				
				if(!fechaComprobada){
					fechaValida(dfFechaExpJefatura.getValue(), dfFechaExpJefatura);
				}
				else if(!mantenerFecha) dfFechaExpJefatura.setValue("");
				fechaComprobada = false;
			}
		});
		
		FormPanel fpFechaExpJefatura = new FormPanel();		
		fpFechaExpJefatura.setBorder(false);			
		
		fpFechaExpJefatura.add(new HTMLD(etiquetas.fechaExpedicionJefatura()));
		fpFechaExpJefatura.add(dfFechaExpJefatura); 		
		
		pRegistroOtrosDatos2.add(vpJefaturaProvTrafico,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos2.add(fpFechaExpJefatura,new ColumnLayoutData(0.33));	
		
		pOtrosDatos.add(pRegistroOtrosDatos1);
		pOtrosDatos.add(pRegistroOtrosDatos2);
		
			
//		TAB PANEL
		tabPanel = new TabPanel();
		tabPanel.setHeight(190);
		tabPanel.setWidth(ANCHURA_REGISTRO);
		tabPanel.setActiveTab(0);
		tabPanel.setFrame(false);
		tabPanel.setBorder(true);
		tabPanel.setPlain(true);

		tabPanel.add(pTabPropietarios);
		tabPanel.add(pTabDirecciones);
		tabPanel.add(pTabSeguros);
		tabPanel.add(pTabImpuestos);
		tabPanel.add(pTabItv);
		tabPanel.add(pOtrosDatos);
		tabPanel.add(pTabDoc);
		tabPanel.add(pExpedientes);
		tabPanel.add(pImagenes);
		
		p.add(fsDatosInsercion);
		p.add(fsDatosVehiculo);
		p.add(tabPanel);		
		p.add(getPanelObervacionesFicheros(false));
		setDisabled(disable);		
		
		return p;
	}
	
	/**
	 * Devuelve un panel que agrupa las observaciones y la subida de ficheros
	 * @return
	 */
	private Panel getPanelObervacionesFicheros(boolean edit) {
		Panel pContenedor = new Panel();
		pContenedor.setLayout(new HorizontalLayout(5));
		
// 		Para subir archivos 
		formUploadImagen = new com.goe.gwt.user.client.ui.FormPanel();
		formUploadImagen.setAction(UPLOAD_ACTION_URL_IMAGEN);
		formUploadImagen.setEncoding(com.goe.gwt.user.client.ui.FormPanel.ENCODING_MULTIPART);
		formUploadImagen.setMethod(com.goe.gwt.user.client.ui.FormPanel.METHOD_POST);

		formUploadFichero = new com.goe.gwt.user.client.ui.FormPanel();
		formUploadFichero.setAction(UPLOAD_ACTION_URL_FICHERO);
		formUploadFichero.setEncoding(com.goe.gwt.user.client.ui.FormPanel.ENCODING_MULTIPART);
		formUploadFichero.setMethod(com.goe.gwt.user.client.ui.FormPanel.METHOD_POST);

//		Se crea un  panel y se pone en el formulario.
		VerticalPanel vpImagen = new VerticalPanel();
		formUploadImagen.setWidget(vpImagen);

		VerticalPanel vpFichero = new VerticalPanel();
		formUploadFichero.setWidget(vpFichero);

//		Se crea el widget FileUpload.
		uploadImagen = new FileUpload();
		uploadImagen.setName("uploadFormElement");
		vpImagen.add(uploadImagen);
		
		uploadFichero = new FileUpload();
		uploadFichero.setName("uploadFormElement");
		vpFichero.add(uploadFichero);
				
//		Se adiciona el control de eventos al formulario para hacer las operaciones necesarias antes de enviar el formulario
		formUploadImagen.addSubmitHandler(new com.goe.gwt.user.client.ui.FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {      		
            }
        });

//		Aqui se administra la respuesta que se retorna a la solicitud actual , en este caso la para saber si se subio el archivo o no.
		formUploadImagen.addSubmitCompleteHandler(new com.goe.gwt.user.client.ui.FormPanel.SubmitCompleteHandler() {
           public void onSubmitComplete(SubmitCompleteEvent event) {
		   }
		});

		formUploadFichero.addSubmitHandler(new com.goe.gwt.user.client.ui.FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {      		
		    }
		});

//		Aqui se administra la respuesta que se retorna a la solicitud actual , en este caso la para saber si se subio el archivo o no.				
		formUploadFichero.addSubmitCompleteHandler(new com.goe.gwt.user.client.ui.FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
			}
		});
		
//		Widgets para subir imágenes y /o archivos
		VerticalPanel vpImagenLabel = new VerticalPanel();
		vpImagenLabel.add(new HTMLD("Adjuntar imagen"));
		vpImagenLabel.add(formUploadImagen);
		
		VerticalPanel vpFicheroLabel = new VerticalPanel();
		vpFicheroLabel.add(new HTMLD("Adjuntar documentaci\u00F3n"));
		vpFicheroLabel.add(formUploadFichero);
		
		Panel pFicheros = new Panel();
		pFicheros.setBorder(false);
		pFicheros.setHeight((ALTURA_CAMPO_FS * 4)-5);
		pFicheros.setLayout(new VerticalLayout(10));
		pFicheros.add(vpImagenLabel);
		pFicheros.add(vpFicheroLabel);
		
//		Fieldset con las opciones de subir ficheros
		FieldSet fsFicheros = new FieldSet(etiquetas.gestionArchivos());
		fsFicheros.setBorder(true);
		fsFicheros.setWidth(270);
		fsFicheros.add(pFicheros);
		
		pContenedor.add(getPanelObservaciones(edit));
		pContenedor.add(fsFicheros);
		return pContenedor;
	}
	
	/**
	 * Devuelve el fieldSet con las observaciones
	 * @return
	 */
	private FieldSetObservaciones getPanelObservaciones(boolean edit) {
		fsObservaciones = new FieldSetObservaciones(Utilidades.etiquetas.observaciones(), ANCHURA_REGISTRO - 273, ALTURA_CAMPO_FS * 4);
		fsObservaciones.setObeservaciones((edit == true ? vehiculo.getObservaciones() : ""));
		return fsObservaciones;
	}


	
	/**
     * Diálogo con el que el usuario obtiene los datos del vehiculo seleccionado y puede modificarlos
     */
	public Panel editarRegistroVehiculo(Vehiculo vehiculo, Panel pContenido, boolean disable) {
		this.vehiculo = vehiculo;
		this.sizeCellTablePersonas = Integer.valueOf(vehiculo.getIdVehiculo());
		this.disable = disable;		
		
		setSize(pContenido);
		setPaddings(10);
		setAutoScroll(true);
		setLayout(new VerticalLayout(5));

		add(getFormulario(vehiculo));
		return this;
		
	}
	
	public Panel getFormulario(Vehiculo vehiculo) {

		Panel p = new Panel();
		p.setPaddings(10);
		p.setLayout(new VerticalLayout(10));
		
//		Fieldset para los datos de la inserción
		FieldSet fsDatosInsercion = new FieldSet(etiquetas.identificacionRegistro());
		fsDatosInsercion.setBorder(true);
		fsDatosInsercion.setWidth(ANCHURA_REGISTRO);
	
		Panel pRegistro1 = new Panel();
		pRegistro1.setBorder(false);
		pRegistro1.setPaddings(5);
		pRegistro1.setLayout(new ColumnLayout());

		com.smartgwt.client.widgets.Button 	btAsociarExpediente = new com.smartgwt.client.widgets.Button(etiquetas.asociarExpediente());
		btAsociarExpediente.setIcon("data-accept16.png");		
		btAsociarExpediente.setWidth(150);
		btAsociarExpediente.setZIndex(0);
	
		btAsociarExpediente.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					
				
			}
		});
		
		
		pRegistro1.add(new HTMLD(etiquetas.agente(), vehiculo.getIdAgente()+""), new ColumnLayoutData(0.35));
		pRegistro1.add(new HTMLD(etiquetas.fecha(), sdf.format(vehiculo.getFechaInsercion())), new ColumnLayoutData(0.38));
		pRegistro1.add(btAsociarExpediente, new ColumnLayoutData(0.27));
		
		fsDatosInsercion.add(pRegistro1);	
		
					
//		Fieldset para los datos del vehiculo
		FieldSet fsDatosVehiculo = new FieldSet(etiquetas.datosVehiculo());
		fsDatosVehiculo.setBorder(true);
		fsDatosVehiculo.setWidth(ANCHURA_REGISTRO);
		
		Panel pRegistro2 = new Panel();
		pRegistro2.setBorder(false);		
		pRegistro2.setLayout(new ColumnLayout());
		pRegistro2.setHeight(ALTURA_CAMPO_FS+5);
				
//		Numero Bastidor
		VerticalPanel vpNumBastidor = new VerticalPanel();
		vpNumBastidor.add(new HTMLD(etiquetas.numBastidor()));
		tfNumBastidor = new TextField(etiquetas.numBastidor(),"numBastidor",120,"");
		tfNumBastidor.setValue(vehiculo.getNumeroBastidor());
		vpNumBastidor.add(tfNumBastidor);		
		
//		Matrícula		
		FormPanel fpMatricula = new FormPanel();		
		fpMatricula.setBorder(false);	
		fpMatricula.add(new HTMLD("* " + etiquetas.matricula(),true));		
		tfMatricula = new TextField();		
		tfMatricula.setWidth(120);
		tfMatricula.setId(Ext.generateId());
		tfMatricula.setHideLabel(true);
		tfMatricula.setAllowBlank(false);
		tfMatricula.setValue(vehiculo.getMatricula());
		tfMatricula.setMaxLength(20);
		tfMatricula.setBlankText(etiquetas.campoMatriculaObligatorio());
		fpMatricula.add(tfMatricula);		
		
		
//		Fecha Matriculacion
		dfFechaMatriculacion = new DateField() ;
		dfFechaMatriculacion.setId(Ext.generateId());
		dfFechaMatriculacion.setFormat("d/m/Y");
		dfFechaMatriculacion.setHideLabel(true);
		dfFechaMatriculacion.setMaxLengthText("10");	
		dfFechaMatriculacion.setMaskRe("[\\d/]+");	
		dfFechaMatriculacion.setWidth(120);
		dfFechaMatriculacion.setBlankText(etiquetas.fechaMatriculacion());

		dfFechaMatriculacion.addListener(new DatePickerListenerAdapter(){
			public void onSelect(DatePicker dataPicker, java.util.Date date){				
				dfFechaMatriculacion.setValue(new Date());
				mantenerFecha = fechaValida(date, dfFechaMatriculacion);	
				fechaComprobada = true;
			}
		});
		
		dfFechaMatriculacion.addListener(new ComboBoxListenerAdapter(){
			public void onBlur(Field field)
			{		
				dfFechaMatriculacion.setAllowBlank(false);
				if(!fechaComprobada){
					fechaValida(dfFechaMatriculacion.getValue(), dfFechaMatriculacion);
				}
				else if(!mantenerFecha) dfFechaMatriculacion.setValue("");
				fechaComprobada = false;
			}
		});
		
		if(vehiculo.getFechaMatriculacion()==null){
			dfFechaMatriculacion.setValue("");
		}
		else{
			dfFechaMatriculacion.setValue(vehiculo.getFechaMatriculacion());
		}
		
		FormPanel fpFechaMatriculacion = new FormPanel();		
		fpFechaMatriculacion.setBorder(false);			
		
		fpFechaMatriculacion.add(new HTMLD(etiquetas.fechaMatriculacion()));
		fpFechaMatriculacion.add(dfFechaMatriculacion); 		
		
//		Color
		VerticalPanel vpColor = new VerticalPanel();
		vpColor.add(new HTMLD(etiquetas.color()));
		tfColor = new TextField(etiquetas.color(),"color",120,"");
		tfColor.setValue(vehiculo.getColor());
		vpColor.add(tfColor);
		
		pRegistro2.add(vpNumBastidor,new ColumnLayoutData(0.25));		
		pRegistro2.add(fpMatricula,new ColumnLayoutData(0.25));
		pRegistro2.add(fpFechaMatriculacion,new ColumnLayoutData(0.25));
		pRegistro2.add(vpColor,new ColumnLayoutData(0.25));
		
		Panel pRegistro3 = new Panel();
		pRegistro3.setBorder(false);		
		pRegistro3.setLayout(new ColumnLayout());
		pRegistro3.setHeight(ALTURA_CAMPO_FS+5);
		
		
//		Tipo
		cbTipos = new CampoLista(etiquetas.tipoVehiculo(), Constantes.TIPOSVEHICULOS, false, false);
		cbTipos.getCombo().addListener(new ComboBoxListenerAdapter() {    
            public void onSelect(ComboBox comboBox, Record record, int index) { 
            	cbMarcas.getCombo().setDisabled(false);
            	cbModelos.getCombo().setDisabled(true);
            	cbMarcas.setValue("");
            	cbModelos.setValue("");
            	cbMarcas.filtrarPorId(cbTipos.getCombo().getValue());            
            }  
        });  
		cbTipos.setValue(vehiculo.getTipoVehiculo());
		
//		Marca
		cbMarcas = new CampoLista(etiquetas.marca(), Constantes.MARCAS, false,true);
		cbMarcas.getCombo().addListener(new ComboBoxListenerAdapter() {    
            public void onSelect(ComboBox comboBox, Record record, int index) { 
            	cbModelos.getCombo().setDisabled(false);
            	cbModelos.setValue("");
            	cbTipos.setValue(record.getAsString("nomDep"));
            	cbModelos.filtrarPorId(cbMarcas.getCombo().getValue());
            }  
        }); 
		cbMarcas.setValue(vehiculo.getMarca());
		
//		Modelo
		cbModelos = new CampoLista(etiquetas.modelo(), Constantes.MODELOS, false,true);
		cbModelos.filtrarPorId(cbMarcas.getCombo().getValue());
		cbModelos.setValue(vehiculo.getModelo());
		
//		Año de fabricacion
		VerticalPanel vpAnioFabricacion = new VerticalPanel();
		vpAnioFabricacion.add(new HTMLD(etiquetas.anioFabricacion()));
		nfAnioFabricacion = new NumberField(etiquetas.anioFabricacion(),"anioFabricacion",120);
		nfAnioFabricacion.setAllowDecimals(false);
		nfAnioFabricacion.setValue(vehiculo.getAnioFabricacion());
		vpAnioFabricacion.add(nfAnioFabricacion);	
		
		pRegistro3.add(cbTipos,new ColumnLayoutData(0.25));
		pRegistro3.add(cbMarcas,new ColumnLayoutData(0.25));
		pRegistro3.add(cbModelos,new ColumnLayoutData(0.25));
		pRegistro3.add(vpAnioFabricacion,new ColumnLayoutData(0.25));
		
		fsDatosVehiculo.add(pRegistro2);
		fsDatosVehiculo.add(pRegistro3);
		
//		TAB PROPIETARIOS
		Panel pTabPropietarios = new Panel();
		pTabPropietarios.setTitle(etiquetas.propietario());
		pTabPropietarios.setBorder(true);		
		pTabPropietarios.setPaddings(5);
		pTabPropietarios.add(getPanelGridPropietarios(vehiculo.getPropietarios(),false));
		
		
//		TAB DIRECCION		
		Panel pTabDirecciones = new Panel();
		pTabDirecciones.setTitle(etiquetas.direccionVehiculo());
		pTabDirecciones.setBorder(true);		
		pTabDirecciones.setPaddings(5);
		pTabDirecciones.add(getPanelGridDirecciones(vehiculo.getDireccionesVehiculo(),false), new ColumnLayoutData(1));
		
		
//		TAB SEGUROS
		Panel pTabSeguros = new Panel();
		pTabSeguros.setTitle(etiquetas.seguros());
		pTabSeguros.setBorder(true);	
		pTabSeguros.add(cargarGridSeguros());
		
//		TAB IMPUESTOS
		Panel pTabImpuestos = new Panel();
		pTabImpuestos.setTitle(etiquetas.impuestos());
		pTabImpuestos.setBorder(true);	
		pTabImpuestos.add(cargarGridImpuestos());	
		
//		TAB ITV		
		Panel pTabItv = new Panel();	
		pTabItv.setBorder(true);
		pTabItv.setTitle(etiquetas.itv());
		pTabItv.add(cargarGridItv());	
		
//		TAB Documentos		
		Panel pTabDoc = new Panel();	
		pTabDoc.setBorder(true);
		pTabDoc.setPaddings(5);
		pTabDoc.setTitle(etiquetas.documentacion());
		pTabDoc.add(getPanelGridDocumentaciones(vehiculo.getDoc(),false));		

//		TabPanel con los expedientes sobre los que está asociada la persona
		Panel pExpedientes = new Panel();
		pExpedientes.setTitle("Expedientes");
		pExpedientes.setBorder(true);
		pExpedientes.setPaddings(10);
		pExpedientes.setLayout(new HorizontalLayout(10));	
		
//		Replicar tantas veces como expedientes tenga asociada la persona
		if(vehiculo != null){
			for (int i = 0; i<5; ++i){
				pExpedientes.add(getExpedientes(i, vehiculo.getMatricula()));
			}
		}

		Panel pImagenes = new Panel();
		pImagenes.setBorder(true);
		pImagenes.setPaddings(5);		
		pImagenes.setTitle(etiquetas.imagen());		
		pImagenes.add(getImagen(vehiculo.getImagen(), vehiculo.getMatricula()));	
		
		
//		TAB OTROS DATOS 
		Panel pOtrosDatos = new Panel();		
		pOtrosDatos.setLayout(new VerticalLayout());
		pOtrosDatos.setPaddings(10);
		pOtrosDatos.setBorder(true);
		pOtrosDatos.setTitle(etiquetas.otrosDatos());
		
		Panel pRegistroOtrosDatos1 = new Panel();
		pRegistroOtrosDatos1.setWidth(ANCHURA_REGISTRO);
		pRegistroOtrosDatos1.setBorder(false);
		pRegistroOtrosDatos1.setLayout(new ColumnLayout());
		pRegistroOtrosDatos1.setHeight(ALTURA_CAMPO_FS+5);
		
//		Tipo Combustible
		clCombustible = new CampoLista(etiquetas.combustible(), Constantes.TIPOS_COMBUSTIBLE, false,false,"120px");	
		clCombustible.setValue(vehiculo.getTipoCombustible());		
		
//		Cilindrada
		VerticalPanel vpCilindrada = new VerticalPanel();
		vpCilindrada.add(new HTMLD(etiquetas.cilindrada()));
		nfCilindrada = new NumberField(etiquetas.cilindrada(),"cilindrada",120);
		nfCilindrada.setAllowDecimals(false);
		nfCilindrada.setValue(vehiculo.getCilindrada());
		vpCilindrada.add(nfCilindrada);	
		
//		Potencia
		VerticalPanel vpPotencia = new VerticalPanel();
		vpPotencia.add(new HTMLD("KW"));
		nfPotencia = new NumberField("KW","potenciaFiscal",120);
		nfPotencia.setAllowDecimals(false);
		nfPotencia.setValue(vehiculo.getPotenciaFiscal());
		vpPotencia.add(nfPotencia);
		
		pRegistroOtrosDatos1.add(clCombustible,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos1.add(vpCilindrada,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos1.add(vpPotencia,new ColumnLayoutData(0.33));	

		Panel pRegistroOtrosDatos2 = new Panel();
		pRegistroOtrosDatos2.setWidth(ANCHURA_REGISTRO);
		pRegistroOtrosDatos2.setBorder(false);
		pRegistroOtrosDatos2.setLayout(new ColumnLayout());
		pRegistroOtrosDatos2.setHeight(ALTURA_CAMPO_FS+5);
		
//		Jefatura Provincial de tráfico
		VerticalPanel vpJefaturaProvTrafico = new VerticalPanel();
		vpJefaturaProvTrafico.add(new HTMLD(etiquetas.jefaturaProvTrafico()));
		tfJefaturaProvTrafico = new TextField(etiquetas.jefaturaProvTrafico(),"jefaturaProvTrafico",120,"");
		tfJefaturaProvTrafico.setValue(vehiculo.getJefaturaProvTrafico());
		vpJefaturaProvTrafico.add(tfJefaturaProvTrafico);	
		
//		Fecha expedición jefatura
		dfFechaExpJefatura = new DateField() ;
		dfFechaExpJefatura.setId(Ext.generateId());
		dfFechaExpJefatura.setFormat("d/m/Y");
		dfFechaExpJefatura.setHideLabel(true);
		dfFechaExpJefatura.setMaxLengthText("10");	
		dfFechaExpJefatura.setMaskRe("[\\d/]+");	
		dfFechaExpJefatura.setWidth(120);
		dfFechaExpJefatura.setBlankText(etiquetas.fechaExpedicionJefatura());
		
		if(vehiculo.getFechaExpedicionJefatura()==null){
			dfFechaExpJefatura.setValue("");
		}
		else{
			dfFechaExpJefatura.setValue(vehiculo.getFechaExpedicionJefatura());
		}
		dfFechaExpJefatura.addListener(new DatePickerListenerAdapter(){
			public void onSelect(DatePicker dataPicker, java.util.Date date){				
				dfFechaExpJefatura.setValue(new Date());
				mantenerFecha = fechaValida(date, dfFechaExpJefatura);	
				fechaComprobada = true;
			}
		});
		
		dfFechaExpJefatura.addListener(new ComboBoxListenerAdapter(){
			public void onBlur(Field field)
			{	
				dfFechaExpJefatura.setAllowBlank(false);
				if(!fechaComprobada){
					fechaValida(dfFechaExpJefatura.getValue(), dfFechaExpJefatura);
				}
				else if(!mantenerFecha) dfFechaExpJefatura.setValue("");
				fechaComprobada = false;
			}
		});
		
		FormPanel fpFechaExpJefatura = new FormPanel();		
		fpFechaExpJefatura.setBorder(false);			
		
		fpFechaExpJefatura.add(new HTMLD(etiquetas.fechaExpedicionJefatura()));
		fpFechaExpJefatura.add(dfFechaExpJefatura); 		
		
		pRegistroOtrosDatos2.add(vpJefaturaProvTrafico,new ColumnLayoutData(0.33));
		pRegistroOtrosDatos2.add(fpFechaExpJefatura,new ColumnLayoutData(0.33));	
		
		pOtrosDatos.add(pRegistroOtrosDatos1);
		pOtrosDatos.add(pRegistroOtrosDatos2);
		
			
//		TAB PANEL
		tabPanel = new TabPanel();
		tabPanel.setHeight(190);
		tabPanel.setWidth(ANCHURA_REGISTRO);
		tabPanel.setActiveTab(0);
		tabPanel.setFrame(false);
		tabPanel.setBorder(true);
		tabPanel.setPlain(true);
		
		tabPanel.add(pTabPropietarios);
		tabPanel.add(pTabDirecciones);
		tabPanel.add(pTabSeguros);
		tabPanel.add(pTabImpuestos);
		tabPanel.add(pTabItv);
		tabPanel.add(pOtrosDatos);
		tabPanel.add(pTabDoc);
		tabPanel.add(pExpedientes);		
		tabPanel.add(pImagenes);

		p.add(fsDatosInsercion);
		p.add(fsDatosVehiculo);
		p.add(tabPanel);		
		p.add(getPanelObervacionesFicheros(true));
		setDisabled(disable);		

		return p;
	}

	public Vehiculo getVehiculo() {
		Record[] records = null;
//		Cargo Propietarios Vehiculo
        propietariosVehiculo= new PersonasAsociaciones();
		for(int i=0;i<gridPropietarios.getStore().getCount();i++){
			propietariosVehiculo.addPersonaAsociacion((PersonaAsociacion)gridPropietarios.getStore().getAt(i).getAsObject("propietarios"));
		}
		
		
		direcciones = new DireccionesAsociaciones();
		for(int i=0;i<gridDirecciones.getStore().getCount();i++){
			direcciones.addContactos((DireccionAsociacion)gridDirecciones.getStore().getAt(i).getAsObject("direccion"));
		}
		
//		Cargo Seguros Vehiculo
		seguros = new Seguros();
		records = gridSeguros.getStore().getRecords();  
        for (int i = 0; i < records.length; i++) {  
            Record record = records[i];            
            seguros.addSeguro((Seguro)record.getAsObject("seguro"));
        } 
        

//		Cargo Impuestos Vehiculo
		impuestos = new Impuestos();
		records = gridImpuestos.getStore().getRecords();  
        for (int i = 0; i < records.length; i++) {  
            Record record = records[i];            
            impuestos.addImpuesto((Impuesto)record.getAsObject("impuesto"));
        }
        
//		Cargo Itvs Vehiculo
		itvs = new Itvs();
		records = gridItvs.getStore().getRecords();  
        for (int i = 0; i < records.length; i++) {  
            Record record = records[i];            
            itvs.addItv((Itv)record.getAsObject("itv"));
        }
		
    	documentaciones = new Documentaciones();
		for(int i=0;i<gridDocumentacion.getStore().getCount();i++){
			documentaciones.addDocumentaciones((Documentacion)gridDocumentacion.getStore().getAt(i).getAsObject("documentacion"));
		}
		
		Vehiculo vehiculo = new Vehiculo(
				1, 
				1, 
				"",
				new Date(),
				tfNumBastidor.getValueAsString(), 
				nfAnioFabricacion.getValueAsString(), 
				tfMatricula.getValueAsString(), 
				cbTipos.getValor(), 
				cbMarcas.getValor(), 
				cbModelos.getValor(), 
				dfFechaMatriculacion.getValue(), 
				tfColor.getValueAsString(), 
				direcciones, 
				seguros, 
				propietariosVehiculo, 
				tfJefaturaProvTrafico.getValueAsString(), 
				dfFechaExpJefatura.getValue(), 
				itvs, 
				impuestos, 
				clCombustible.getValor(), 
				nfCilindrada.getValueAsString(), 
				nfPotencia.getValueAsString(), 
				documentaciones,
				fsObservaciones.getObservaciones().toString(),
				"images/iVehiculo.png");
		
		return vehiculo;
	}


	private PanelVehiculo getInstance() {
		return this;
	}	
	
	public void setSize(Panel pContenido){
		ALTURA_CAMPO_FS = 50;
		
		if (pContenido.getWidth() < Constantes.ANCHURA_PANTALLA_REG_MIN){
			ANCHURA_PIZQD = Constantes.ANCHURA_PANTALLA_REG_MIN-460;
			ANCHURA_REGISTRO = Constantes.ANCHURA_PANTALLA_REG_MIN-530;
			
			ANCHURA_DIALOGO_REG = Constantes.ANCHURA_PANTALLA_REG_MIN;
		}else{
			ANCHURA_PIZQD = pContenido.getWidth()-460;
			ANCHURA_REGISTRO = pContenido.getWidth()-530;
			
			ANCHURA_DIALOGO_REG = pContenido.getWidth();
		}
		if (pContenido.getHeight() < Constantes.ALTURA_PANTALLA_REG_MIN){
			ALTURA_PANTALLA_REG = Constantes.ALTURA_PANTALLA_REG_MIN-24;
			ALTURA_DIALOGO_REG = Constantes.ALTURA_PANTALLA_REG_MIN-24;
		}else{
			ALTURA_PANTALLA_REG = pContenido.getHeight()-24;
			ALTURA_DIALOGO_REG = pContenido.getHeight()-24;
		}
	}
	
	
	
	/**
     * Obtenemos el grid con los datos
     */
	private GridPanel getPanelGridDirecciones(DireccionesAsociaciones dirs, final boolean remove) {		
		
		gridDirecciones = new GridPanel();	
		gridDirecciones.setFrame(false);
		gridDirecciones.setHeight(ALTURA_CAMPO_FS*3);
//		gridDirecciones.setWidth((ANCHURA_REGISTRO)-50);
		modeloSeleccionDireccion = new RowSelectionModel(true);
		gridDirecciones.setSelectionModel(modeloSeleccionDireccion);
		gridDirecciones.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				super.onRowClick(grid, rowIndex, e);
				
			}
			@Override
		public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
				
				
				if (!disable){
					DireccionAsociacion dir = (DireccionAsociacion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
					
					PanelGestionDireccion p = new PanelGestionDireccion(parentPanel);
					p.initMappDatos(dir.getIdDireccion());
					DialogoGestion<Direccion> dlg = new DialogoGestion<Direccion>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
					dlg.getPanel(p, dir.getIdDireccion()-1, true);
				}
				
		}
	});

		gridDirecciones.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
				if (colIndex == 3 && !disable){
					DireccionAsociacion dir = (DireccionAsociacion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
					
					PanelGestionDireccion p = new PanelGestionDireccion(parentPanel);
					p.initMappDatos(dir.getIdDireccion());
					DialogoGestion<Direccion> dlg = new DialogoGestion<Direccion>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
					dlg.getPanel(p, dir.getIdDireccion()-1, true);
				}
				
				if ((colIndex == 4 ) && (remove)){
					
					gridDirecciones = removeRow(gridDirecciones, modeloSeleccionDireccion);
					
				}else{
					if (colIndex == 2){
//						Direccion dir = (Direccion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
						/*
						DialogoRegistroDireccion diri = new DialogoRegistroDireccion(getInstance());
						diri.mostrarRegistroDireccion(dir);*/
					}
				}
				
				if (colIndex == 3){
//					Direccion dir = (Direccion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
					/*
					DialogoRegistroDireccion diri = new DialogoRegistroDireccion(getInstance());
					diri.mostrarRegistroDireccion(dir);
					*/
				}
				
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		recordsDirecciones = new RecordDef(
				new FieldDef[]{ 
					new IntegerFieldDef("index"),						
					new ObjectFieldDef("direccion"),
                    new StringFieldDef("direccionStr"),
                    new StringFieldDef("fechaInicioStr"),
                    new StringFieldDef("fechaFinStr"),
                    });

		if (remove){
			proxyDirecciones = new MemoryProxy(new Object[0][0]);
			storeDirecciones = new Store(proxyDirecciones,new ArrayReader(recordsDirecciones));
						
			gridDirecciones.setStore(storeDirecciones);
			
		}else{
			int numDirecciones = dirs.numDireccionesAsociaciones();
			objDireccion = new Object[numDirecciones][10];				

			for(int i=0; i<dirs.numDireccionesAsociaciones(); i++){
				objDireccion[i] = new Object[]{
						i,
						dirs.getDireccionesAsociaciones().get(i),
						//ACCESS BBDD
						CellTableDireccion.getDireccion(dirs.getDireccionesAsociaciones().get(i).getIdDireccion()).getDireccionCompleta(),
						sdf.format(dirs.getDireccionesAsociaciones().get(i).getFecini()),
						dirs.getDireccionesAsociaciones().get(i).getFecfin() != null ? sdf.format(dirs.getDireccionesAsociaciones().get(i).getFecfin()) : ""
						}; 
	        }
			proxyDirecciones = new MemoryProxy(objDireccion);	
			storeDirecciones = new Store(proxyDirecciones,new ArrayReader(recordsDirecciones));
			
			gridDirecciones.setStore(storeDirecciones);
		}
		
//		CAMPOS GRID Direcciones
			
		ColumnConfig colDireccionCompleta, colFechaInicio, colFechaFin, colEditar, colVerDatos, colEliminar;
		
		//colDireccionCompleta
		colDireccionCompleta = new ColumnConfig(etiquetas.direccion(), "direccionStr");
		colDireccionCompleta.setWidth(300);
		colDireccionCompleta.setAlign(TextAlign.LEFT);
		
		//colFechaInicio
		colFechaInicio = new ColumnConfig(etiquetas.fechaInicio(), "fechaInicioStr");
		colFechaInicio.setWidth(100);
		colFechaInicio.setAlign(TextAlign.LEFT);

		//colFechaFin
		colFechaFin = new ColumnConfig(etiquetas.fechaFin(), "fechaFinStr");
		colFechaFin.setWidth(100);
		colFechaFin.setAlign(TextAlign.LEFT);


		
		//colVerEditar
		colEditar = new ColumnConfig("", "editar", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}

			}
		});
		colEditar.setAlign(TextAlign.CENTER);

		
		//colVerDatos
		colVerDatos = new ColumnConfig("", "datos", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=16 height=16>";
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, true, new Renderer() {		
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
				public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
					return "<img src=\"images/icCancelar.png\" width=16 height=16>";
				}
			});
		colEliminar.setAlign(TextAlign.CENTER);

		ColumnModel colModel = null;
		if (remove){
		colModel = new ColumnModel(
				new BaseColumnConfig[]{
						colDireccionCompleta, colFechaInicio, colFechaFin, colEditar, colEliminar});
		}else{
		colModel = new ColumnModel(
					new BaseColumnConfig[]{
							colDireccionCompleta, colFechaInicio, colFechaFin, colEditar});
		}

		//Toobar Insertar Direccions
		Toolbar tbDireccion = new Toolbar();
		ToolbarButton tbNuevo = null;
		tbNuevo = new ToolbarButton(etiquetas.aniadirDireccion(),new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
				if (tfMatricula.getValueAsString().isEmpty()) {
					dialogo = new DialogoSmart(etiquetas.direccion(), etiquetas
							.rellenarDatosObligatorios(), listenerOK,
							Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();
				} else {
				super.onClick(button, e);
				/*
				DialogoRegistroDireccion diri = new DialogoRegistroDireccion(getInstance());
				direccion = null;
				diri.nuevoRegistro();
				*/
				PanelGestion<Direccion> p = new PanelGestionDireccion(parentPanel);
				DialogoGestion<Direccion> dlg = new DialogoGestion<Direccion>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
				dlg.getPanel(p);

				direccion = null;
				}
			}
		});
		tbNuevo.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		tbNuevo.setDisabled(disable);
		tbDireccion.addButton(tbNuevo);

		
		colModel.setDefaultSortable(true);
		gridDirecciones.setColumnModel(colModel);
		gridDirecciones.setAutoExpandColumn(colFechaFin.getId());
		gridDirecciones.setTopToolbar(tbDireccion);
		storeDirecciones.load(0, 10);
		

				
		return gridDirecciones;
	}

	
	
	/**
     * Obtenemos el grid con los datos
     */
	private GridPanel getPanelGridPropietarios(PersonasAsociaciones dirs, final boolean remove) {		
		
		gridPropietarios = new GridPanel();	
		gridPropietarios.setFrame(false);
		gridPropietarios.setHeight(ALTURA_CAMPO_FS*3);
//		gridPropietarios.setWidth((ANCHURA_REGISTRO)-50);
		modeloSeleccionPropietarios = new RowSelectionModel(true);
		gridPropietarios.setSelectionModel(modeloSeleccionPropietarios);
		gridPropietarios.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				super.onRowClick(grid, rowIndex, e);
				
			}
			@Override
		public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
				
				
				if (!disable){
					PersonaAsociacion per = (PersonaAsociacion) modeloSeleccionPropietarios.getSelected().getAsObject("propietarios");				
					
					PanelGestionPersonas p = new PanelGestionPersonas(parentPanel, ((PanelGestionVehiculo) parentPanel).acceso );
					DialogoGestion<Persona> dlg = new DialogoGestion<Persona>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
					dlg.getPanel(p, per.getIdPersona()-1, true);
				}
				
		}
	});

		gridPropietarios.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
				if (colIndex == 6 && !disable){
					PersonaAsociacion per = (PersonaAsociacion) modeloSeleccionPropietarios.getSelected().getAsObject("propietarios");				
					
					PanelGestionPersonas p = new PanelGestionPersonas(parentPanel, ((PanelGestionVehiculo) parentPanel).acceso );
					DialogoGestion<Persona> dlg = new DialogoGestion<Persona>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
					dlg.getPanel(p, per.getIdPersona()-1, true);

					
				}
				
				if ((colIndex == 7 ) && (remove)){
					
					gridPropietarios = removeRow(gridPropietarios, modeloSeleccionPropietarios);
					
				}else{
					if (colIndex == 2){
//						Direccion dir = (Direccion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
						/*
						DialogoRegistroDireccion diri = new DialogoRegistroDireccion(getInstance());
						diri.mostrarRegistroDireccion(dir);*/
					}
				}
				
				if (colIndex == 3){
//					Direccion dir = (Direccion) modeloSeleccionDireccion.getSelected().getAsObject("direccion");
					/*
					DialogoRegistroDireccion diri = new DialogoRegistroDireccion(getInstance());
					diri.mostrarRegistroDireccion(dir);
					*/
				}
				
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		recordsPropietarios = new RecordDef(
				new FieldDef[]{ 
						new IntegerFieldDef("index"),						
						new ObjectFieldDef("propietarios"),
	                    new StringFieldDef("apellidosStr"),
	                    new StringFieldDef("nombreStr"),
	                    new StringFieldDef("dniStr"),
	                    new StringFieldDef("fechaInicioStr"),
	                    new StringFieldDef("fechaFinStr"),
	                    new StringFieldDef("ObservacionesStr")
	                    });

		if (remove){
			proxyPropietarios = new MemoryProxy(new Object[0][0]);
			storePropietarios = new Store(proxyPropietarios,new ArrayReader(recordsPropietarios));
						
			gridPropietarios.setStore(storePropietarios);
			
		}else{
			int numPropietarios = dirs.numPersonasAsociacion();
			objfilePropietarios = new Object[numPropietarios][10];				

			for(int i=0; i<dirs.numPersonasAsociacion(); i++){
				objfilePropietarios[i] = new Object[]{
						i,
						dirs.getPersonasAsociacion().get(i),
						CellTablePersona.getPersona(dirs.getPersonasAsociacion().get(i).getIdPersona()).getApellido1() + " " + CellTablePersona.getPersona(dirs.getPersonasAsociacion().get(i).getIdPersona()).getApellido2(),
						CellTablePersona.getPersona(dirs.getPersonasAsociacion().get(i).getIdPersona()).getNombre(),
						CellTablePersona.getPersona(dirs.getPersonasAsociacion().get(i).getIdPersona()).getDni(),
						sdf.format(dirs.getPersonasAsociacion().get(i).getFechaInicio()),
						dirs.getPersonasAsociacion().get(i).getFechaFin() != null ? sdf.format(dirs.getPersonasAsociacion().get(i).getFechaFin()) : "",				
						CellTablePersona.getPersona(dirs.getPersonasAsociacion().get(i).getIdPersona()).getObservaciones()						
						}; 
				
	        }
			proxyPropietarios = new MemoryProxy(objfilePropietarios);	
			storePropietarios = new Store(proxyPropietarios,new ArrayReader(recordsPropietarios));
			
			gridPropietarios.setStore(storePropietarios);
		}
		
//		CAMPOS GRID avisoAccidente
		
		ColumnConfig colApellidos, colNombre, colDni,  colFechaInicio, colFechaFin, colObservaciones, colEditar, colVerDatos, colEliminar;
		
		//colApellidos
        colApellidos = new ColumnConfig(etiquetas.apellidos(), "apellidosStr");
        colApellidos.setWidth(100);
        colApellidos.setAlign(TextAlign.LEFT);
        
		//colNombre
        colNombre = new ColumnConfig(etiquetas.nombre(), "nombreStr");
        colNombre.setWidth(100);
        colNombre.setAlign(TextAlign.LEFT);

		//colDni
        colDni = new ColumnConfig(etiquetas.dni(), "dniStr");
        colDni.setWidth(100);
        colDni.setAlign(TextAlign.LEFT);

		//colFechaInicio
		colFechaInicio = new ColumnConfig(etiquetas.fechaInicio(), "fechaInicioStr");
		colFechaInicio.setWidth(100);
		colFechaInicio.setAlign(TextAlign.LEFT);
		
		//colFechaFin
		colFechaFin = new ColumnConfig(etiquetas.fechaFin(), "fechaFinStr");
		colFechaFin.setWidth(100);
		colFechaFin.setAlign(TextAlign.LEFT);
      		
		//colObservaciones
        colObservaciones = new ColumnConfig(etiquetas.observaciones(), "ObservacionesStr");
        colObservaciones.setWidth(100);
        colObservaciones.setAlign(TextAlign.LEFT);

		
		//colVerEditar
		colEditar = new ColumnConfig("", "editar", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}

			}
		});
		colEditar.setAlign(TextAlign.CENTER);

		
		//colVerDatos
		colVerDatos = new ColumnConfig("", "datos", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=16 height=16>";
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, true, new Renderer() {		
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
				public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
					return "<img src=\"images/icCancelar.png\" width=16 height=16>";
				}
			});
		colEliminar.setAlign(TextAlign.CENTER);

		ColumnModel colModel = null;
		if (remove){
		colModel = new ColumnModel(
				new BaseColumnConfig[]{
						colApellidos, colNombre, colDni,  colFechaInicio, colFechaFin, colObservaciones, colEditar, colEliminar});
		}else{
		colModel = new ColumnModel(
					new BaseColumnConfig[]{
							colApellidos, colNombre, colDni,  colFechaInicio, colFechaFin, colObservaciones, colEditar});
		}

		//Toobar Insertar Direccions
		Toolbar tbDireccion = new Toolbar();
		ToolbarButton tbNuevo = null;
		tbNuevo = new ToolbarButton(etiquetas.aniadir() + " " + etiquetas.propietario(),new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
				if (tfMatricula.getValueAsString().isEmpty()) {
					dialogo = new DialogoSmart(etiquetas.propietario(), etiquetas
							.rellenarDatosObligatorios(), listenerOK,
							Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();
				} else {
					super.onClick(button, e);
					PanelGestion<Persona> per = new PanelGestionPersonas(parentPanel, ((PanelGestionVehiculo) parentPanel).acceso );
					DialogoGestion<Persona> dlg = new DialogoGestion<Persona>(getInstance(), "", ANCHURA_DIALOGO_REG, ALTURA_DIALOGO_REG);
					dlg.getPanel(per);
					propietariosVehiculo = null;
				}
			}
		});
		tbNuevo.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		tbNuevo.setDisabled(disable);
		tbDireccion.addButton(tbNuevo);

		
		colModel.setDefaultSortable(true);
		gridPropietarios.setColumnModel(colModel);
		gridPropietarios.setAutoExpandColumn(colObservaciones.getId());
		gridPropietarios.setTopToolbar(tbDireccion);
		storePropietarios.load(0, 10);
		

				
		return gridPropietarios;
	}

	private Panel cargarGridSeguros(){	
		gridSeguros = new GridPanel();			
		btAddSeguro = new Button();
		btAddSeguro.setText(etiquetas.aniadirSeguro());
		btAddSeguro.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		btAddSeguro.setDisabled(disable);
		btAddSeguro.addListener(new ButtonListenerAdapter(){
			public void onClick(Button button, EventObject e){
				if(tfMatricula.getValueAsString().isEmpty()){
					dialogo = new DialogoSmart(etiquetas.seguros(), etiquetas.rellenarDatosObligatorios(),
							listenerOK, Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();	
				}
				else{
					DialogoSeguro dirs = new DialogoSeguro(getInstance(), parentPanel);
					dirs.nuevoRegistro();
				}
			}
		});			
		gridSeguros.setTopToolbar(btAddSeguro);
		
		modeloSeleccionSeguros = new RowSelectionModel(true);
		gridSeguros.setSelectionModel(modeloSeleccionSeguros);
		gridSeguros.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				modeloSeleccionSeguros.selectRow(rowIndex);	
				posicion = rowIndex;
				super.onRowClick(grid, rowIndex, e);							
			}	
		});		
		
		gridSeguros.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
//				Editar
				if (colIndex == 5 && !disable){										
					DialogoSeguro dirs = new DialogoSeguro(getInstance(), parentPanel);
					dirs.editarRegistroSeguro((Seguro) modeloSeleccionSeguros.getSelected().getAsObject("seguro"));
				}
//				Eliminar
				else if (colIndex == 6 && eliminarActivo){	
					gridSeguros.getStore().remove(modeloSeleccionSeguros.getSelected());					
				}
//				Ver detalle
				else if (colIndex == 7 || (colIndex == 6 && !eliminarActivo)){	
//					DialogoMostrarRegistroSeguro dmrs = new DialogoMostrarRegistroSeguro();
//					dmrs.showDialog((Seguro) modeloSeleccionSeguros.getSelected().getAsObject("seguro"));
				}				
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		int numRegistros = ((vehiculo == null || vehiculo.getSeguros() == null)?0:vehiculo.getSeguros().numSeguros());
		objfileSeguros = new Object[numRegistros][6];
		for(int i=0; i<numRegistros;i++){
			objfileSeguros[i] = new Object[]{ 
					vehiculo.getSeguros().getSeguro(i),
					vehiculo.getSeguros().getSeguro(i).getCompania(),
					vehiculo.getSeguros().getSeguro(i).getNumeroPoliza(),
					vehiculo.getSeguros().getSeguro(i).getFechaInicioCobertura(),
					vehiculo.getSeguros().getSeguro(i).getPeriodoCobertura(),
					vehiculo.getSeguros().getSeguro(i).getTomador().getDatosCompletosPersona()
			};			
		}
		
		recordsSeguros = new RecordDef(  
				new FieldDef[]{			
						new ObjectFieldDef("seguro"),
						new StringFieldDef("compania"),
						new StringFieldDef("numeroPoliza"),
						new DateFieldDef("fechaInicioCobertura"),		
						new StringFieldDef("periodoCobertura"),
						new StringFieldDef("tomador")
				}  
		);
		
		proxySeguros = new MemoryProxy(objfileSeguros);	 
		storeSeguros = new Store(proxySeguros, new ArrayReader(recordsSeguros));  
		storeSeguros.load();	

	
		gridSeguros.setStore(storeSeguros);
		gridSeguros.setColumnModel(getModeloGridSeguro());	
		gridSeguros.setStripeRows(true);		 
		gridSeguros.setAutoExpandColumn("compania");  
				
		gridSeguros.setHeight(ALTURA_CAMPO_FS*3);

		Panel panelGrid = new Panel();	       
		panelGrid.setBorder(false);
	    panelGrid.setPaddings(5);  
		panelGrid.add(gridSeguros);			
		return panelGrid;
			
	}
	
	private ColumnModel getModeloGridSeguro(){
		ColumnConfig colCompania ,colfechaInicioCobertura, colNumeroPoliza, colPeriodoCobertura, colTomador, colEditar, colEliminar, colVerDatos;
		
		colfechaInicioCobertura = new ColumnConfig(etiquetas.fechaInicioCobertura(), "fechaInicioCobertura", 120, true, new Renderer() {			
			@Override
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if(value==null){				
					return "";
				}
				else{
					return sdf.format((Date) value);
				}
			}		
		});
		colfechaInicioCobertura.setAlign(TextAlign.CENTER);
		
		colPeriodoCobertura = new ColumnConfig(etiquetas.periodoCobertura(), "periodoCobertura");
		colPeriodoCobertura.setId("periodoCobertura");
		colPeriodoCobertura.setWidth(150);	
		colPeriodoCobertura.setAlign(TextAlign.CENTER);
		
		colCompania = new ColumnConfig(etiquetas.compania(), "compania");
		colCompania.setId("compania");
		colCompania.setWidth(150);		
		colCompania.setAlign(TextAlign.CENTER);
				

		colNumeroPoliza = new ColumnConfig(etiquetas.numeroPoliza(), "numeroPoliza");
		colNumeroPoliza.setId("numeroPoliza");
		colNumeroPoliza.setWidth(120);		
		colNumeroPoliza.setAlign(TextAlign.CENTER);

		colTomador = new ColumnConfig(etiquetas.tomador(), "tomador");
		colTomador.setId("tomador");
		colTomador.setWidth(150);		
		colTomador.setAlign(TextAlign.CENTER);
	
		
		colEditar = new ColumnConfig("", "editar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}
			}
		});
		colEditar.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icCancelar.png\" width=\"16\" height=\"16\">";
			}
		});
		colEliminar.setAlign(TextAlign.CENTER);

		colVerDatos = new ColumnConfig("", "verDatos", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=\"16\" height=\"16\">";				
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		ColumnModel modelo = new ColumnModel(
				(eliminarActivo)?
				new BaseColumnConfig[]{
						colNumeroPoliza,
						colCompania,
						colfechaInicioCobertura,
						colPeriodoCobertura,
						colTomador,
						colEditar,
						colEliminar,
				}:
				new BaseColumnConfig[]{
						colNumeroPoliza,
						colCompania,
						colfechaInicioCobertura,
						colPeriodoCobertura,
						colTomador,
						colEditar,					
				}				
		);		
		modelo.setDefaultSortable(true);
		return modelo;
	}	
	
	
	
	private Panel cargarGridImpuestos(){	
		gridImpuestos = new GridPanel();			
		btAddImpuesto = new Button();
		btAddImpuesto.setText(etiquetas.aniadirImpuesto());
		btAddImpuesto.setDisabled(disable);
		btAddImpuesto.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		btAddImpuesto.addListener(new ButtonListenerAdapter(){
			public void onClick(Button button, EventObject e){
				if(tfMatricula.getValueAsString().isEmpty()){
					dialogo = new DialogoSmart(etiquetas.impuesto(), etiquetas.rellenarDatosObligatorios(),
							listenerOK, Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();	
				}
				else{
					DialogoInsertarRegistroImpuesto diri = new DialogoInsertarRegistroImpuesto(getInstance());
					diri.nuevoRegistro();
				}
			}
		});			
		gridImpuestos.setTopToolbar(btAddImpuesto);
		
		modeloSeleccionImpuestos = new RowSelectionModel(true);
		gridImpuestos.setSelectionModel(modeloSeleccionImpuestos);
		gridImpuestos.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				modeloSeleccionImpuestos.selectRow(rowIndex);	
				posicion = rowIndex;
				super.onRowClick(grid, rowIndex, e);							
			}	
		});		
		
		gridImpuestos.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
//				Editar
				if (colIndex == 5 && !disable){										
					DialogoInsertarRegistroImpuesto diri = new DialogoInsertarRegistroImpuesto(getInstance());
					diri.editarRegistroImpuesto((Impuesto) modeloSeleccionImpuestos.getSelected().getAsObject("impuesto"));
				}
//				Eliminar
				else if (colIndex == 6 && eliminarActivo){	
					gridImpuestos.getStore().remove(modeloSeleccionImpuestos.getSelected());					
				}
//				Ver detalle
				else if (colIndex == 7 || (colIndex == 6 && !eliminarActivo)){	
					DialogoMostrarRegistroImpuesto dmri = new DialogoMostrarRegistroImpuesto();
					dmri.showDialog((Impuesto) modeloSeleccionImpuestos.getSelected().getAsObject("impuesto"));
				}				
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		int numRegistros = ((vehiculo == null || vehiculo.getImpuestos() == null)?0:vehiculo.getImpuestos().numImpuestos());
		objfileImpuestos = new Object[numRegistros][6];
		for(int i=0; i<numRegistros;i++){
			objfileImpuestos[i] = new Object[]{ 
					vehiculo.getImpuestos().getImpuesto(i),
					vehiculo.getImpuestos().getImpuesto(i).getTipoImpuesto(),
					vehiculo.getImpuestos().getImpuesto(i).getDescripcion(),
					vehiculo.getImpuestos().getImpuesto(i).getFechaPago(),
					vehiculo.getImpuestos().getImpuesto(i).getImporte(),
					vehiculo.getImpuestos().getImpuesto(i).getPeriodoFacturacion()
			};			
		}
		
		recordsImpuestos = new RecordDef(  
				new FieldDef[]{			
						new ObjectFieldDef("impuesto"),
						new StringFieldDef("tipoImpuesto"),
						new StringFieldDef("descripcion"),
						new DateFieldDef("fechaPago"),		
						new FloatFieldDef("importe"),	
						new StringFieldDef("periodoFacturacion")
				}  
		);
		
		proxyImpuestos = new MemoryProxy(objfileImpuestos);	 
		storeImpuestos = new Store(proxyImpuestos, new ArrayReader(recordsImpuestos));  
		storeImpuestos.load();	

	
		gridImpuestos.setStore(storeImpuestos);
		gridImpuestos.setColumnModel(getModeloGridImpuesto());	
		gridImpuestos.setStripeRows(true);		 
		gridImpuestos.setAutoExpandColumn("descripcion");  
				
		gridImpuestos.setHeight(ALTURA_CAMPO_FS*3);	

		Panel panelGrid = new Panel();	       
		panelGrid.setBorder(false);
	    panelGrid.setPaddings(5);  
		panelGrid.add(gridImpuestos);			
		return panelGrid;
			
	}
	
	private ColumnModel getModeloGridImpuesto(){
		ColumnConfig colDescripcion ,colFechaPago, colTipoImpuesto, colImporte, colEditar, colPeriodoFacturacion, colEliminar, colVerDatos;
		
		colFechaPago = new ColumnConfig(etiquetas.fechaPago(), "fechaPago", 100, true, new Renderer() {			
			@Override
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if(value==null){
					return "";
				}
				else{
					return sdf.format((Date) value);
				}
			}		
		});
		colFechaPago.setAlign(TextAlign.CENTER);
		
		colDescripcion = new ColumnConfig(etiquetas.descripcion(), "descripcion");
		colDescripcion.setId("descripcion");
		colDescripcion.setWidth(150);		
		colDescripcion.setAlign(TextAlign.CENTER);
				

		colTipoImpuesto = new ColumnConfig(etiquetas.tipoImpuesto(), "tipoImpuesto");
		colTipoImpuesto.setId("tipoImpuesto");
		colTipoImpuesto.setWidth(100);		
		colTipoImpuesto.setAlign(TextAlign.CENTER);
		
		colImporte = new ColumnConfig(etiquetas.importe(), "importe");
		colImporte.setId("importe");
		colImporte.setWidth(120);		
		colImporte.setAlign(TextAlign.CENTER);
		
		colPeriodoFacturacion = new ColumnConfig(etiquetas.periodoFacturacion(), "periodoFacturacion");
		colPeriodoFacturacion.setId("periodoFacturacion");
		colPeriodoFacturacion.setWidth(120);		
		colPeriodoFacturacion.setAlign(TextAlign.CENTER);
	
		
		colEditar = new ColumnConfig("", "editar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}
			}
		});
		colEditar.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icCancelar.png\" width=\"16\" height=\"16\">";
			}
		});
		colEliminar.setAlign(TextAlign.CENTER);

		colVerDatos = new ColumnConfig("", "verDatos", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=\"16\" height=\"16\">";				
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		ColumnModel modelo = new ColumnModel(
				(eliminarActivo)?
				new BaseColumnConfig[]{
						colTipoImpuesto,
						colDescripcion,
						colFechaPago,
						colImporte,
						colPeriodoFacturacion,						
						colEditar,
						colEliminar,
						colVerDatos
				}:
				new BaseColumnConfig[]{
						colTipoImpuesto,
						colDescripcion,
						colFechaPago,
						colImporte,
						colPeriodoFacturacion,						
						colEditar,					
						colVerDatos
				}				
		);		
		modelo.setDefaultSortable(true);
		return modelo;
	}	
	
	
	private Panel cargarGridItv(){	
		gridItvs = new GridPanel();	
		btAddItv = new Button();
		btAddItv.setText(etiquetas.aniadirItv());
		btAddItv.setDisabled(disable);
		btAddItv.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		btAddItv.addListener(new ButtonListenerAdapter(){
			public void onClick(Button button, EventObject e){
				if(tfMatricula.getValueAsString().isEmpty()){
					dialogo = new DialogoSmart(etiquetas.itv(), etiquetas.rellenarDatosObligatorios(),
							listenerOK, Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();	
				}
				else{
					DialogoInsertarRegistroItv diri = new DialogoInsertarRegistroItv(getInstance());
					diri.nuevoRegistro();
				}
			}
		});			
		gridItvs.setTopToolbar(btAddItv);
		
		modeloSeleccionItvs = new RowSelectionModel(true);
		gridItvs.setSelectionModel(modeloSeleccionItvs);
		gridItvs.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				modeloSeleccionItvs.selectRow(rowIndex);	
				posicion = rowIndex;
				super.onRowClick(grid, rowIndex, e);							
			}	
		});		
		
		gridItvs.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
//				Editar
				if (colIndex == 3 && !disable){											
					DialogoInsertarRegistroItv diri = new DialogoInsertarRegistroItv(getInstance());
					diri.editarRegistroItv((Itv) modeloSeleccionItvs.getSelected().getAsObject("itv"));
				}
//				Eliminar
				else if (colIndex == 4 && eliminarActivo){	
					gridItvs.getStore().remove(modeloSeleccionItvs.getSelected());					
				}
//				Ver detalle
				else if (colIndex == 5 || (colIndex == 4 && !eliminarActivo)){	
					DialogoMostrarRegistroItv dmri = new DialogoMostrarRegistroItv();
					dmri.showDialog((Itv) modeloSeleccionItvs.getSelected().getAsObject("itv"));
				}				
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		int numRegistros = ((vehiculo == null || vehiculo.getItvs() == null)?0:vehiculo.getItvs().numItvs());
		objfileItvs = new Object[numRegistros][3];
		for(int i=0; i<numRegistros;i++){
			objfileItvs[i] = new Object[]{ 
					vehiculo.getItvs().getItv(i),				
					vehiculo.getItvs().getItv(i).getEstacionITV(),
					vehiculo.getItvs().getItv(i).getFechaITV(),
					vehiculo.getItvs().getItv(i).getFechaProximaITV()
			};			
		}
		

		recordsItvs = new RecordDef(  
				new FieldDef[]{			
						new ObjectFieldDef("itv"),							
	                    new StringFieldDef("estacionItv"),
	                    new DateFieldDef("fechaItv"),	
						new DateFieldDef("fechaProximaItv")
				}  
		);
		
		proxyItvs = new MemoryProxy(objfileItvs);	 
		storeDirecciones = new Store(proxyItvs, new ArrayReader(recordsItvs));  
		storeDirecciones.load();	

	
		gridItvs.setStore(storeDirecciones);
		gridItvs.setColumnModel(getModeloGridItv());	
		gridItvs.setStripeRows(true);		 
		gridItvs.setAutoExpandColumn("estacionItv");  
				
		gridItvs.setHeight(ALTURA_CAMPO_FS*3);	

		Panel panelGrid = new Panel();	       
		panelGrid.setBorder(false);
	    panelGrid.setPaddings(5);  
		panelGrid.add(gridItvs);			
		return panelGrid;
			
	}
	
	private ColumnModel getModeloGridItv(){
		ColumnConfig colEstacionItv ,colFechaItv, colFechaProximaItv,  colEditar, colEliminar, colVerDatos;
	
		colFechaItv = new ColumnConfig(etiquetas.fechaITV(), "fechaItv", 150, true, new Renderer() {			
			@Override
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if(value==null){
					return "";
				}
				else{
					return sdf.format((Date) value);
				}
			}		
		});
		colFechaItv.setAlign(TextAlign.CENTER);
		
		colEstacionItv = new ColumnConfig(etiquetas.estacionITV(), "estacionItv");
		colEstacionItv.setId("estacionItv");
		colEstacionItv.setWidth(150);		
		colEstacionItv.setAlign(TextAlign.LEFT);
				
		colFechaProximaItv = new ColumnConfig(etiquetas.fechaProximaItv(), "fechaProximaItv", 150, true, new Renderer() {			
			@Override
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if(value==null){
					return "";
				}
				else{
					return sdf.format((Date) value);
				}
			}		
		});
		colFechaProximaItv.setAlign(TextAlign.CENTER);
		
	
		
		colEditar = new ColumnConfig("", "editar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object,	  com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}
			}
		});
		colEditar.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icCancelar.png\" width=\"16\" height=\"16\">";
			}
		});
		colEliminar.setAlign(TextAlign.CENTER);

		colVerDatos = new ColumnConfig("", "verDatos", 30, false, new Renderer() {
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=\"16\" height=\"16\">";				
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		ColumnModel modelo = new ColumnModel(
				(eliminarActivo)?
				new BaseColumnConfig[]{						
						colEstacionItv,
						colFechaItv,
						colFechaProximaItv,
						colEditar,
						colEliminar,
						colVerDatos
				}:
				new BaseColumnConfig[]{
					colEstacionItv,
					colFechaItv,
					colFechaProximaItv,
					colEditar,
					colVerDatos
				}				
		);		
		modelo.setDefaultSortable(true);
		return modelo;
	}	
	
	
	
	/**
     * Obtenemos el grid con los datos
     */
	private GridPanel getPanelGridDocumentaciones(Documentaciones documentaciones, final boolean remove) {		
		
		gridDocumentacion = new GridPanel();	
		gridDocumentacion.setFrame(false);
		gridDocumentacion.setHeight(ALTURA_CAMPO_FS*3);
//		gridDocumentacion.setWidth((ANCHURA_REGISTRO)-50);
		modeloSeleccionDocumentacion = new RowSelectionModel(true);
		gridDocumentacion.setSelectionModel(modeloSeleccionDocumentacion);
		gridDocumentacion.addGridRowListener(new GridRowListenerAdapter(){
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject e) {
				super.onRowClick(grid, rowIndex, e);
				
			}
			@Override
		public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
				
				if (!disable){
					Documentacion com = (Documentacion) modeloSeleccionDocumentacion.getSelected().getAsObject("documentacion");
					DialogoRegistroDocumentaciones documen = new DialogoRegistroDocumentaciones(getInstance());
					documen.editarRegistro(com);
				}
				
		}
	});

		gridDocumentacion.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,	EventObject e) {
				if (colIndex == 4 && !disable){
					Documentacion com = (Documentacion) modeloSeleccionDocumentacion.getSelected().getAsObject("documentacion");
					DialogoRegistroDocumentaciones documen = new DialogoRegistroDocumentaciones(getInstance());
					documen.editarRegistro(com);
				}
				
				if ((colIndex == 5 ) && (remove)){
					
					gridDocumentacion = removeRow(gridDocumentacion, modeloSeleccionDocumentacion);

				}else{
					if (colIndex == 5){
						Documentacion com = (Documentacion) modeloSeleccionDocumentacion.getSelected().getAsObject("documentacion");
						DialogoRegistroDocumentaciones documen = new DialogoRegistroDocumentaciones(getInstance());
						documen.mostrarRegistro(com);
					}
				}
				
				if (colIndex == 6){
					Documentacion com = (Documentacion) modeloSeleccionDocumentacion.getSelected().getAsObject("documentacion");
					DialogoRegistroDocumentaciones documen = new DialogoRegistroDocumentaciones(getInstance());
					documen.mostrarRegistro(com);
				}
			}
			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,	int cellIndex, EventObject e) {}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,int colIndex, EventObject e) {}
			
		});
		
		recordsDocumentacion= new RecordDef(
				new FieldDef[]{ 
					new IntegerFieldDef("index"),
					new ObjectFieldDef("documentacion"),
                    new StringFieldDef("tipoStr"),
					new StringFieldDef("nombreStr"),
					new StringFieldDef("fechaInicioStr"),
					new StringFieldDef("fechaFinStr"),
                    });

		if (remove){
			proxyDocumentacion = new MemoryProxy(new Object[0][0]);
			storeDocumentacion = new Store(proxyDocumentacion,new ArrayReader(recordsDocumentacion));
						
			gridDocumentacion.setStore(storeDocumentacion);
			
		}else{
			int numDocumentaciones = documentaciones.numDocumentaciones();
			objDocumentacion = new Object[numDocumentaciones][10];				

			for(int i=0; i<documentaciones.numDocumentaciones(); i++){
				objDocumentacion[i] = new Object[]{
						i,
						documentaciones.getDocumentaciones().get(i),
						documentaciones.getDocumentaciones().get(i).getDocumento(),				
						documentaciones.getDocumentaciones().get(i).getNombreDocumento(),
						sdf.format(documentaciones.getDocumentaciones().get(i).getFecini()),
						sdf.format(documentaciones.getDocumentaciones().get(i).getFecfin())
						}; 
	        }
			proxyDocumentacion = new MemoryProxy(objDocumentacion);
			storeDocumentacion = new Store(proxyDocumentacion,new ArrayReader(recordsDocumentacion));
			
			gridDocumentacion.setStore(storeDocumentacion);
		}
		
//		CAMPOS GRID Telefono
			
		ColumnConfig colTipo, colValor, colfechaInicio, colFechaFin, colEditar, colVerDatos, colEliminar;

		
		//colTipo
		colTipo = new ColumnConfig("Tipo", "tipoStr");
		colTipo.setWidth(150);
		colTipo.setAlign(TextAlign.LEFT);

		//colValor
		colValor = new ColumnConfig(etiquetas.nombre(), "nombreStr");
		colValor.setWidth(150);
		colValor.setAlign(TextAlign.LEFT);
		
		//colfechaInicio
		colfechaInicio = new ColumnConfig(etiquetas.fechaInicio(), "fechaInicioStr");
		colfechaInicio.setWidth(150);
		colfechaInicio.setAlign(TextAlign.LEFT);

		//colFechaFin
		colFechaFin = new ColumnConfig(etiquetas.fechaFin(), "fechaFinStr");
		colFechaFin.setWidth(150);
		colFechaFin.setAlign(TextAlign.LEFT);
		
		//colVerEditar
		colEditar = new ColumnConfig("", "editar", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				if (!disable){
					return "<img src=\"images/iceditar.png\" width=16 height=16>";
				}else{
					return "<img src=\"images/iceditar_Disabled.png\" width=16 height=16>";	
				}

			}
		});
		colEditar.setAlign(TextAlign.CENTER);

		
		//colVerDatos
		colVerDatos = new ColumnConfig("", "datos", 30, true, new Renderer() {		
		/*
		 * (non-Javadoc)
		 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
		 */
			public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
				return "<img src=\"images/icVerVentana.png\" width=16 height=16>";
			}
		});
		colVerDatos.setAlign(TextAlign.CENTER);
		
		colEliminar = new ColumnConfig("", "eliminar", 30, true, new Renderer() {		
			/*
			 * (non-Javadoc)
			 * @see com.gwtext.client.widgets.grid.Renderer#render(java.lang.Object, com.gwtext.client.widgets.grid.CellMetadata, com.gwtext.client.data.Record, int, int, com.gwtext.client.data.Store)
			 */
				public String render(Object value, com.gwtext.client.widgets.grid.CellMetadata cellMetadata, com.gwtext.client.data.Record record, int rowIndex, int colNum, Store store) {
					return "<img src=\"images/icCancelar.png\" width=16 height=16>";
				}
			});
		colEliminar.setAlign(TextAlign.CENTER);

		ColumnModel colModel = null;
		if (remove){
		colModel = new ColumnModel(
				new BaseColumnConfig[]{
						colTipo, colValor, colfechaInicio, colFechaFin, colEditar, colEliminar, colVerDatos});
		}else{
		colModel = new ColumnModel(
					new BaseColumnConfig[]{
							colTipo, colValor, colfechaInicio, colFechaFin, colEditar, colVerDatos});
		}

		//Toobar Insertar Documentacion
		Toolbar tbDocumentacion = new Toolbar();
		ToolbarButton tbNuevo = null;
		tbNuevo = new ToolbarButton(etiquetas.aniadir() + " " + etiquetas.documentacion(),new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
				if (tfMatricula.getValueAsString().isEmpty()) {
					dialogo = new DialogoSmart(etiquetas.documentacion(), etiquetas
							.rellenarDatosObligatorios(), listenerOK,
							Constantes.ICONO_MENSAJE_INFO);
					dialogo.show();
				} else {
				super.onClick(button, e);
				DialogoRegistroDocumentaciones documen = new DialogoRegistroDocumentaciones(getInstance());
				documentacion = null;
				documen.nuevoRegistro();
				}
			}
		});
		tbNuevo.setIcon(Constantes.ICONO_NUEVO_REGISTRO);
		tbNuevo.setDisabled(disable);
		tbDocumentacion.addButton(tbNuevo);
		
		colModel.setDefaultSortable(true);
		gridDocumentacion.setColumnModel(colModel);
		gridDocumentacion.setAutoExpandColumn(colFechaFin.getId());
		gridDocumentacion.setTopToolbar(tbNuevo);
		gridDocumentacion.setStripeRows(true);
		storeDocumentacion.load(0, 10);
				
		return gridDocumentacion;
	}

	
	
	public void setPropietario(Persona perso, boolean update){
		this.propietarioVehiculo = new PersonaAsociacion(); 
		this.propietarioVehiculo.setIdPersona(perso.getIdPersona());
		DialogoRegistroFechas<Persona> fechas = new DialogoRegistroFechas<Persona>(getInstance());
		if (update){
			fechas.editarRegistro(modeloSeleccionPropietarios.getSelected().getAsString("fechaInicioStr"), modeloSeleccionPropietarios.getSelected().getAsString("fechaFinStr"), perso);
		}else{
			fechas.nuevoRegistro(perso);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void setPropietarioVehiculo(Date fechaInicio, Date fechaFin, boolean update){
		this.propietarioVehiculo.setFechaInicio(fechaInicio);
		if (fechaFin != null)
			this.propietarioVehiculo.setFechaFin(fechaFin);
		boolean up = false;
		up = update;
		if (up){
			//Eliminamos el registro seleccionado
			int index = modeloSeleccionPropietarios.getSelected().getAsInteger("index"); //posicion en el vector
			gridPropietarios.getStore().remove(modeloSeleccionPropietarios.getSelected());
			gridPropietarios.getStore().sort("index", "ASC");			
//			if (this.propietarioVehiculo.getPropietario().getFechaNacimiento() == null){
//				this.propietarioVehiculo.getPropietario().setFechaNacimiento(new Date());
//			}
			Record record =	recordsPropietarios.createRecord(String.valueOf(this.propietarioVehiculo.hashCode()),new Object[]{
				index,
				this.propietarioVehiculo,
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getApellido1() + " " + CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getApellido2(),
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getNombre(),
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getDni(),
				sdf.format(this.propietarioVehiculo.getFechaInicio()),
				(this.propietarioVehiculo.getFechaFin()) != null ? sdf.format(this.propietarioVehiculo.getFechaFin()) : "",
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getObservaciones()						

			});
			
			gridPropietarios.getStore().insert(index, record);  		
			gridPropietarios.getView().refresh();	

		}else{
			int index = gridPropietarios.getStore().getCount();
//			if (this.propietarioVehiculo.getPropietario().getFechaNacimiento() == null){
//				this.propietarioVehiculo.getPropietario().setFechaNacimiento(new Date());
//			}
			Record record =	recordsPropietarios.createRecord(String.valueOf(this.propietarioVehiculo.hashCode()),new Object[]{
				index,
				this.propietarioVehiculo,
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getApellido1() + " " + CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getApellido2(),
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getNombre(),
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getDni(),
				sdf.format(this.propietarioVehiculo.getFechaInicio()),
				(this.propietarioVehiculo.getFechaFin()) != null ? sdf.format(this.propietarioVehiculo.getFechaFin()) : "",
				CellTablePersona.getPersona(this.propietarioVehiculo.getIdPersona()).getObservaciones()						
			});
			gridPropietarios.getStore().add(record);  	
			gridPropietarios.getView().refresh();
		}
	}
	
	public void setDireccion(Direccion dire, boolean update){
		this.direccion = new DireccionAsociacion(); 
		this.direccion.setIdDireccion(dire.getIdDireccion());
		DialogoRegistroFechas<Direccion> fechas = new DialogoRegistroFechas<Direccion>(getInstance());
		if (update){
			fechas.editarRegistro(modeloSeleccionDireccion.getSelected().getAsString("fechaInicioStr"), modeloSeleccionDireccion.getSelected().getAsString("fechaFinStr"), dire);
		}else{
			fechas.nuevoRegistro(dire);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void setDireccionVehiculo(Date fechaInicio, Date fechaFin, boolean update){
		this.direccion.setFecini(fechaInicio);
		if (fechaFin != null)
			this.direccion.setFecfin(fechaFin);
		boolean up = false;
		up = update;
		if (up){
			//Eliminamos el registro seleccionado
			int index = modeloSeleccionDireccion.getSelected().getAsInteger("index"); //posicion en el vector
			gridDirecciones.getStore().remove(modeloSeleccionDireccion.getSelected());
			gridDirecciones.getStore().sort("index", "ASC");			
			Record record =	recordsDirecciones.createRecord(String.valueOf(this.direccion.hashCode()),new Object[]{
				index,
				this.direccion,
				CellTableDireccion.getDireccion(this.direccion.getIdDireccion()).getDireccionCompleta(),
				sdf.format(this.direccion.getFecini()),
				(this.direccion.getFecfin() != null) ? sdf.format(this.direccion.getFecfin()) : ""
			});
			gridDirecciones.getStore().insert(index, record);  		
			gridDirecciones.getView().refresh();	

		}else{
			int index = gridDirecciones.getStore().getCount();
			Record record =	recordsDirecciones.createRecord(String.valueOf(this.direccion.hashCode()),new Object[]{
				index,
				this.direccion,
				CellTableDireccion.getDireccion(this.direccion.getIdDireccion()).getDireccionCompleta(),
				sdf.format(this.direccion.getFecini()),
				(this.direccion.getFecfin() != null) ? sdf.format(this.direccion.getFecfin()) : ""
			});
			gridDirecciones.getStore().add(record);  	
			gridDirecciones.getView().refresh();
		}
	}
	
	public void setSeguro(Seguro seguro, boolean update) {
		
		Record record =	recordsSeguros.createRecord(String.valueOf(seguro.hashCode()),new Object[]{
			seguro,							
			seguro.getCompania(),
			seguro.getNumeroPoliza(),				
			seguro.getFechaInicioCobertura(),
			seguro.getPeriodoCobertura(),
			(seguro.getTomador()==null)?"":seguro.getTomador().getDatosCompletosPersona()
		});		

		if(update){			
			gridSeguros.getStore().remove(modeloSeleccionSeguros.getSelected());
			gridSeguros.getStore().insert(posicion, record);
		}
		else{
			gridSeguros.getStore().add(record);
		}	
		gridSeguros.getView().refresh();
	}

	
	public void setImpuesto(Impuesto impuesto, boolean update){
		
		Record record =	recordsImpuestos.createRecord(String.valueOf(impuesto.hashCode()),new Object[]{
			impuesto,
			impuesto.getTipoImpuesto(),
			impuesto.getDescripcion(),
			impuesto.getFechaPago(),		
			impuesto.getImporte(),
			impuesto.getPeriodoFacturacion()		
		});		
		
		if(update){			
			gridImpuestos.getStore().remove(modeloSeleccionImpuestos.getSelected());
			gridImpuestos.getStore().insert(posicion, record);
		}
		else{
			gridImpuestos.getStore().add(record);
		}
		gridImpuestos.getView().refresh();	
		
	}
	
	
	public void setItv(Itv itv, boolean update){
		
		Record record =	recordsItvs.createRecord(String.valueOf(itv.hashCode()),new Object[]{
			itv,				
			itv.getEstacionITV(),
			itv.getFechaITV(),		
			itv.getFechaProximaITV()			
		});		
		
		if(update){			
			gridItvs.getStore().remove(modeloSeleccionItvs.getSelected());
			gridItvs.getStore().insert(posicion, record);
		}
		else{
			gridItvs.getStore().add(record);
		}
		gridItvs.getView().refresh();	
		
	}
	
	public void setDocumentacion(Documentacion documentacion, boolean update){
		this.documentacion = documentacion;
		boolean up = false;
		up = update;
		if (up){
			//Eliminamos el registro seleccionado
			int index = modeloSeleccionDocumentacion.getSelected().getAsInteger("index"); //posicion en el vector
			gridDocumentacion.getStore().remove(modeloSeleccionDocumentacion.getSelected());
			gridDocumentacion.getStore().sort("index", "ASC");			
			Record record =	recordsDocumentacion.createRecord(String.valueOf(this.documentacion.hashCode()),new Object[]{
				index,
				this.documentacion,
				this.documentacion.getDocumento(),
				this.documentacion.getNombreDocumento(),
				sdf.format(this.documentacion.getFecini()),
				sdf.format(this.documentacion.getFecfin()), 
			});
			gridDocumentacion.getStore().insert(index, record);  		
			gridDocumentacion.getView().refresh();	

		}else{
			int index = gridDocumentacion.getStore().getCount();
			Record record =	recordsDocumentacion.createRecord(String.valueOf(this.documentacion.hashCode()),new Object[]{
				index,
				this.documentacion,
				this.documentacion.getDocumento(),
				this.documentacion.getNombreDocumento(),
				sdf.format(this.documentacion.getFecini()),
				sdf.format(this.documentacion.getFecfin()), 
			});
			gridDocumentacion.getStore().add(record);  	
			gridDocumentacion.getView().refresh();
		}
	}

		
	public boolean fechaValida(Date date, DateField fecha){
//		Comprobamos que la fecha es correcta
		if(!fecha.isValid() || !date.before(new Date())){
			if(!dialogoVisible){
				dialogo = new DialogoSmart(etiquetas.fechaInvalida(), etiquetas.fechaPosteriorActual(),
						listenerOK, Constantes.ICONO_MENSAJE_INFO);
				dialogo.show();		
				dialogoVisible = true;
				fecha.setValue("");
			}
			return false;
		}		
		return true;
	}
	
	public String getDatosVehiculo(){
		return tfMatricula.getValueAsString();
	}
	
	@SuppressWarnings("deprecation")
	public GridPanel removeRow(GridPanel grid, RowSelectionModel modeloSeleccion){
		int index = modeloSeleccion.getSelected().getAsInteger("index"); //posicion en el vector

		//Borro registro actual
		grid.getStore().remove(modeloSeleccion.getSelected());

		//Actualizamos (restauramos) indices
		for(int i=index; i<grid.getStore().getCount();i++){
			grid.getStore().getAt(i).set("index", i);
		}
		grid.getStore().sort("index", "ASC");
		grid.getView().refresh();
		
		return grid;
	}
	
	
	public void setDisabled(boolean disabled){
		
		tfNumBastidor.setCls("disabled");
		tfMatricula.setCls("disabled");
		dfFechaMatriculacion.setCls("disabled");
		nfAnioFabricacion.setCls("disabled");
		tfColor.setCls("disabled");
		clCombustible.getCombo().setCls("disabled");
		nfCilindrada.setCls("disabled");
		nfPotencia.setCls("disabled");
		tfJefaturaProvTrafico.setCls("disabled");
		dfFechaExpJefatura.setCls("disabled");
		cbMarcas.getCombo().setCls("disabled");
		cbModelos.getCombo().setCls("disabled");
		cbTipos.getCombo().setCls("disabled");
		fsObservaciones.setCtCls("disabled");
		
	
		//Set Disabled
		tfNumBastidor.setDisabled(disabled);
		tfMatricula.setDisabled(disabled);
		dfFechaMatriculacion.setDisabled(disabled);
		nfAnioFabricacion.setDisabled(disabled);
		tfColor.setDisabled(disabled);
		clCombustible.getCombo().setDisabled(disabled);
		nfCilindrada.setDisabled(disabled);
		nfPotencia.setDisabled(disabled);
		tfJefaturaProvTrafico.setDisabled(disabled);
		dfFechaExpJefatura.setDisabled(disabled);
		cbMarcas.getCombo().setDisabled(disabled);
		cbModelos.getCombo().setDisabled(disabled);
		cbTipos.getCombo().setDisabled(disabled);
		fsObservaciones.setDisabled(disabled);
	
	}
	
	/**
	 * Devuelve una imagen de la persona en un verticalPanel
	 * @param i
	 * @param nombre
	 * @return
	 */
	private VerticalPanel getImagen(String imagen, String nombre){
		VerticalPanel vpImagen = new VerticalPanel();
		Label lbFecha = new Label(sdf.format(new Date())+"_"+imagen);
		BotonEnlace btImagen = new BotonEnlace(imagen,imagen,imagen, nombre);
		btImagen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		}); 
		btImagen.setSize("48px", "66px");
		vpImagen.add(btImagen);
		vpImagen.add(lbFecha);
		vpImagen.setCellHorizontalAlignment(btImagen, HasHorizontalAlignment.ALIGN_CENTER);
		vpImagen.setCellHorizontalAlignment(lbFecha, HasHorizontalAlignment.ALIGN_CENTER);
		return vpImagen;
	}

	/**
	 * Devuelve un verticalPanel con una imagen y un texto referido a un expediente relacionado con la persona
	 * @param i
	 * @param nombre
	 * @return
	 */
	private VerticalPanel getExpedientes(int i, String nombre){
		VerticalPanel vpExpediente = new VerticalPanel();
		Label lbFecha = new Label(sdf.format(new Date())+" Fecha"+i);
		BotonEnlace btExpediente = new BotonEnlace("images/btlibros.png","images/btlibros.png", "images/btlibros.png", nombre);
//		Ver el expediente
		btExpediente.addClickHandler(new ClickHandler() {
			/*
			 * (non-Javadoc)
			 * @see com.goe.gwt.event.dom.client.ClickHandler#onClick(com.goe.gwt.event.dom.client.ClickEvent)
			 */
			public void onClick(ClickEvent event) {
				
			}
		}); 
		btExpediente.setSize("48px", "48px");
		vpExpediente.add(btExpediente);
		vpExpediente.add(lbFecha);
		vpExpediente.setCellHorizontalAlignment(btExpediente, HasHorizontalAlignment.ALIGN_CENTER);
		vpExpediente.setCellHorizontalAlignment(lbFecha, HasHorizontalAlignment.ALIGN_CENTER);
		return vpExpediente;
	}





}
