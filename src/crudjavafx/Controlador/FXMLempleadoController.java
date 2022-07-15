/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavafx.Controlador;

import crudjavafx.dao.Empleadosdao;
import crudjavafx.modelo.empleado;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLempleadoController implements Initializable {
    
     @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cboEstado;

    @FXML
    private CheckBox cbxCorreo;

    @FXML
    private CheckBox cbxFisico;

    @FXML
    private CheckBox cbxPortalWeb;

    @FXML
    private DatePicker dpFechaContratacion;

    @FXML
    private RadioButton rbAseador;

    @FXML
    private RadioButton rbCocinero;

    @FXML
    private RadioButton rbMesero;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtNombre;
    
    @FXML
    private TableView<empleado> tvEmpleados;
    
    @FXML
    private ContextMenu cmOpciones;
    
    private Empleadosdao empleadosDao;
    
    private empleado empleadoSelect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String[] opciones = {"Contratado","Por contratar","Temporal","Despedido"};
        
        ObservableList<String> items =  FXCollections.observableArrayList(opciones);
        
        cboEstado.setItems(items);
        cboEstado.setValue("Seleccione");
        
        ToggleGroup group=new ToggleGroup();
        
        rbCocinero.setToggleGroup(group);
        rbMesero.setToggleGroup(group);
        rbAseador.setToggleGroup(group);
        
        this.empleadosDao = new Empleadosdao();
        
        cargarEmpleados();
        
        cmOpciones = new ContextMenu();
        
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        
        
        cmOpciones.getItems().addAll(miEditar, miEliminar);
        
        tvEmpleados.setContextMenu(cmOpciones);
        
        miEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                int index = tvEmpleados.getSelectionModel().getSelectedIndex();
                
               empleadoSelect = tvEmpleados.getItems().get(index);
               
                System.out.println(empleadoSelect);
                
                txtNombre.setText(empleadoSelect.getNombre());
                txtApellido.setText(empleadoSelect.getApellido());
                
                switch(empleadoSelect.getPuesto()){
                    
                    case "Concienro": rbCocinero.setSelected(true);
                    break;
                    case "Mesero": rbMesero.setSelected(true);
                    break;
                    case "Aseador": rbAseador.setSelected(true);
                    break;
                }
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                
                dpFechaContratacion.setValue(LocalDate.parse(empleadoSelect.getFecha_contratacion(), formatter));
                
                cbxCorreo.setSelected(empleadoSelect.getCorreo().equals("Si") ? true : false);
                cbxPortalWeb.setSelected(empleadoSelect.getPortal_web().equals("Si") ? true : false);
                cbxFisico.setSelected(empleadoSelect.getFisico().equals("Si")? true : false);
               
                cboEstado.getSelectionModel().select(empleadoSelect.getEstado());
                
                btnCancelar.setDisable(false);
            }
            
            
            
        });
   
        
        miEliminar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                int index = tvEmpleados.getSelectionModel().getSelectedIndex();
                empleado empleadoEliminar = tvEmpleados.getItems().get(index);
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
                
                alert.setTitle("Confirmacion");
                alert.setHeaderText(null);
                alert.setContentText("Esta seguro que quiere eliminar el empleado: " +empleadoEliminar.getNombre()+"?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get()== ButtonType.OK ){
                    
                    boolean rsp = empleadosDao.eliminar(empleadoEliminar.getId());
                    
                     if( rsp ){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION );

                alert2.setTitle("Exito");
                alert2.setHeaderText(null);
                alert2.setContentText("Los datos del empleado fueron eliminados con exito");
                alert2.initStyle(StageStyle.UTILITY);
                alert2.showAndWait();
                
                cargarEmpleados();

            }else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);

                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText("Algó no salió bien... No fue posible eliminar el empleado");
                alert2.initStyle(StageStyle.UTILITY);
                alert2.showAndWait();

            }   
                }
                
            }
        });
    }    
    
    @FXML
    void btnGuardarOnAction(ActionEvent event) {
        
        if(empleadoSelect == null){
         empleado empleado = new empleado();
        
        empleado.setNombre(txtNombre.getText());
        empleado.setApellido(txtApellido.getText());
        
        if(rbCocinero.isSelected()){
            empleado.setPuesto("Cocinero");
            
        } else if( rbMesero.isSelected()){
            empleado.setPuesto("Mesero"); 
            
        } else if( rbAseador.isSelected()){
            empleado.setPuesto("Aseador");
        }
            
        empleado.setFecha_contratacion(dpFechaContratacion.getValue().toString());
        empleado.setCorreo(cbxCorreo.isSelected()== true ? "Si" : "No");
        empleado.setPortal_web(cbxPortalWeb.isSelected()== true ? "Si" : "No");
        empleado.setFisico(cbxFisico.isSelected()== true ? "Si" : "No");
        
        
        empleado.setEstado(cboEstado.getSelectionModel().getSelectedItem() );
        
        System.out.println( empleado.toString() );
        
        boolean rsp = this.empleadosDao.registrar(empleado);
        
        if( rsp ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION );
            
            alert.setTitle("Exito");
            alert.setHeaderText(null);
            alert.setContentText("El empleado fue registrado con exito");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            
            limpiarCampos();
            cargarEmpleados();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Algó no salió bien... No fue posible registrar el empleado");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            
        }   
        }else {
            
            empleadoSelect.setNombre(txtNombre.getText());
            empleadoSelect.setApellido(txtApellido.getText());
            
            if(rbCocinero.isSelected()){
            empleadoSelect.setPuesto("Cocinero");
            
        } else if( rbMesero.isSelected()){
            empleadoSelect.setPuesto("Mesero"); 
            
        } else if( rbAseador.isSelected()){
            empleadoSelect.setPuesto("Aseador");
        }
            
        empleadoSelect.setFecha_contratacion(dpFechaContratacion.getValue().toString());
        empleadoSelect.setCorreo(cbxCorreo.isSelected()== true ? "Si" : "No");
        empleadoSelect.setPortal_web(cbxPortalWeb.isSelected()== true ? "Si" : "No");
        empleadoSelect.setFisico(cbxFisico.isSelected()== true ? "Si" : "No");
        
        
        empleadoSelect.setEstado(cboEstado.getSelectionModel().getSelectedItem() );
        
        
        boolean rsp = this.empleadosDao.editar(empleadoSelect);
        
        if( rsp ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION );
            
            alert.setTitle("Exito");
            alert.setHeaderText(null);
            alert.setContentText("Los datos del empleado fueron editados con exito");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            
            limpiarCampos();
            cargarEmpleados();
            empleadoSelect = null;
            btnCancelar.setDisable(true);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Algó no salió bien... No fue posible editar el empleado");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            
        }   
        }

        
    }
    
    private void limpiarCampos(){
        txtNombre.setText("");
        txtApellido.setText("");
        rbCocinero.setSelected(true);
        rbMesero.setSelected(false);
        rbAseador.setSelected(false);
        dpFechaContratacion.setValue(null);
        cbxCorreo.setSelected(false);
        cbxPortalWeb.setSelected(false);
        cbxFisico.setSelected(false);
        cboEstado.getSelectionModel().select("Seleccione");
        
                   
    }
    
    public void cargarEmpleados(){
        
        tvEmpleados.getItems().clear();
        tvEmpleados.getColumns().clear();
        
        List<empleado> empleados = this.empleadosDao.listar();
        
        ObservableList<empleado> data = FXCollections.observableArrayList(empleados);
        
        TableColumn idCol =  new TableColumn("Id");
        
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn NomCol = new TableColumn("Nombre");
        
        NomCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        TableColumn ApeCol = new TableColumn("Apellido");
        
        ApeCol.setCellValueFactory(new PropertyValueFactory("apellido"));
        
        TableColumn puestoCol = new TableColumn("Puesto");
        
        puestoCol.setCellValueFactory(new PropertyValueFactory("puesto"));
        
        TableColumn fecha_contCol = new TableColumn("fecha de contratacion");
        
        fecha_contCol.setCellValueFactory(new PropertyValueFactory("fecha_contratacion"));
        
        TableColumn correoCol = new TableColumn("Correo");
        
        correoCol.setCellValueFactory(new PropertyValueFactory("correo"));
        
        TableColumn PortalCol = new TableColumn("Portal Web");
        
        PortalCol.setCellValueFactory(new PropertyValueFactory("portal_web"));
        
        TableColumn fisicoCol = new TableColumn("Fisico");
        
        fisicoCol.setCellValueFactory(new PropertyValueFactory("fisico"));
        
        TableColumn estadoCol = new TableColumn("Estado");
        
        estadoCol.setCellValueFactory(new PropertyValueFactory("estado"));
        
        
        
        
        tvEmpleados.setItems(data);
        tvEmpleados.getColumns().addAll(idCol, NomCol,ApeCol,puestoCol,fecha_contCol,
                correoCol,PortalCol,fisicoCol,estadoCol);  
        
    } 
    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        
        empleadoSelect=null;
        
        limpiarCampos();
        
        btnCancelar.setDisable(true);

    }
}
