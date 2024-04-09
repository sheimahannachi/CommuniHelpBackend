/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import edu.esprit.entities.history;
import edu.esprit.services.historyCRUD;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JFileChooser;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ConsulterHistoriqueController implements Initializable {

    /**
     * Initializes the controller class.
     */

   @FXML
    private Label nbdon;
    @FXML
    private TableColumn<?, ?> idCartetv;

    @FXML
    private TableColumn<?, ?> idCausetv;

    @FXML
    private TableColumn<?, ?> idDontv;

    @FXML
    private TableColumn<?, ?> idVilletv;

    @FXML
    private TableView<history> tableViewDon;


    @FXML
    private Button btnImporter11;

    @FXML
    private Button btnconsulter;
    
    @FXML
    private Label totaldon;


    @FXML
    private Button btnExport;
    @FXML
    private Button eventactionbtn;
    @FXML
    private Button shopbtnaction;
    @FXML
    private Button retbtn;
    
  @FXML
void handleExportButtonAction(ActionEvent event) {
   FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File selectedFile = fileChooser.showSaveDialog(tableViewDon.getScene().getWindow());

    if (selectedFile != null) {
        try {
//PDType1Font font = PDType1Font.HELVETICA_BOLD;
PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define your table layout
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            int rows = tableViewDon.getItems().size();
            int cols = tableViewDon.getColumns().size();
            float rowHeight = 20f;
            float tableHeight = rowHeight * (float) (rows + 1);
            float tableWidthPercent = 100f;

            // Define column widths and calculate tableWidth
            float[] columnWidths = new float[cols];
            for (int i = 0; i < cols; i++) {
                TableColumn<?, ?> column = (TableColumn<?, ?>) tableViewDon.getColumns().get(i);
                double colWidth = column.getWidth();
                columnWidths[i] = (float) colWidth / (float) tableViewDon.getWidth() * tableWidth;
            }
contentStream.beginText();
contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
contentStream.newLineAtOffset(margin, yStart + 20); // Adjust the offset for positioning
contentStream.showText("Merci pour votre aide");
contentStream.endText();
            // Draw the table headers
            float currentX = margin;
            float tableYPosition = yStart - 1 * rowHeight;
            
            
            for (int i = 0; i < cols; i++) {
                contentStream.beginText();
contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                 //   contentStream.setFont(font, 12); // Set the font and font size
                contentStream.newLineAtOffset(currentX, tableYPosition);
    contentStream.showText(((TableColumn<?, ?>) tableViewDon.getColumns().get(i)).getText()); // Use the column name as the header text                
    contentStream.endText();
                currentX += columnWidths[i];
            }

            // Draw the table content
            currentX = margin;
            for (int i = 0; i < rows; i++) {
                tableYPosition -= rowHeight;
                currentX = margin;
                ObservableList<TableColumn<history, ?>> columns = tableViewDon.getColumns();
                for (int j = 0; j < cols; j++) {
                    TableColumn column = columns.get(j);
                   // String cellValue = column.getCellData(i).toString(); // Get cell data from the table
                   Object cellData = column.getCellData(i);
                    String cellValue = (cellData != null) ? cellData.toString() : "";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, tableYPosition);
                    contentStream.showText(cellValue);
                    contentStream.endText();
                    currentX += columnWidths[j] +10;
                }
            }

            contentStream.close();

            // Save the PDF document
            document.save(selectedFile);
            document.close();

            // Optionally, open the exported PDF using the default PDF viewer
            Desktop.getDesktop().open(selectedFile);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}

      
      
 
   @FXML
      public void handlethisButtonAction(ActionEvent event) {
    try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin2.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
        
        
     

  @Override
public void initialize(URL url, ResourceBundle rb) {
    idDontv.setCellValueFactory(new PropertyValueFactory<>("montant"));
    idCausetv.setCellValueFactory(new PropertyValueFactory<>("description"));
    idVilletv.setCellValueFactory(new PropertyValueFactory<>("articleVille"));
    idCartetv.setCellValueFactory(new PropertyValueFactory<>("CarteBancaire"));

    // Call the method to populate the table
    populateHistoryTable();

    tableViewDon.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableViewDon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Handle item selection if needed
        }
    });
    
historyCRUD historyCRUD = new historyCRUD();
int donationCount = historyCRUD.getDonationCount();
nbdon.setText("" + donationCount);
float totalDonationAmount = calculateTotalDonationAmount(historyCRUD.getHistory());
    totaldon.setText("" + totalDonationAmount + " DT");

}
private float calculateTotalDonationAmount(List<history> historyList) {
    float totalAmount = 0;
    for (history record : historyList) {
        totalAmount += record.getMontant();
    }
    return totalAmount;
}
public void populateHistoryTable() {
    // Clear existing items from the table
    tableViewDon.getItems().clear();

    // Retrieve data from your data source (e.g., using HistoryCRUD)
    historyCRUD historyCRUD = new historyCRUD();
    List<history> historyList = historyCRUD.getHistory();

    // Convert the list of history records to an ObservableList
    ObservableList<history> historyData = FXCollections.observableArrayList(historyList);

    // Add the data to the TableView
    tableViewDon.setItems(historyData);
}

    @FXML
    private void eventactionbtnMethod(ActionEvent event) {
        
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eventadmin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        
    }

    @FXML
    private void shopbtnactionMethod(ActionEvent event) {
            try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminshop.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void retaction(ActionEvent event) {
           try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }



}


