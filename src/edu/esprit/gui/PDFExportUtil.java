/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PDFExportUtil {

    public static void exportNodeToPDF(Node node) {
        // Capture a snapshot of the node
        WritableImage nodeSnapshot = node.snapshot(new SnapshotParameters(), null);

        // Save the snapshot as a PNG image
        File imageFile = new File("node_snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeSnapshot, null), "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a new PDF document
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();

        // Add the PNG image to the PDF
        PDImageXObject pdImage;
        PDPageContentStream contentStream;

        try {
            pdImage = PDImageXObject.createFromFile("node_snapshot.png", doc);
            contentStream = new PDPageContentStream(doc, page);
            contentStream.drawImage(pdImage, 100, 100);
            contentStream.close();

            doc.addPage(page);

            // Save the PDF file
            doc.save("exported_node.pdf");
            doc.close();

            // Delete the temporary image file
            imageFile.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}