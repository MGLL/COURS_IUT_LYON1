/*
 * Filtre.java
 *
 * Created on 23 avril 2007, 11:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package selectfich;

/**
 *
 * @author Moonesh.Ramburrun
 */
public class Filtre extends javax.swing.filechooser.FileFilter {
 //le filtre n'acceptera, ici, que les fichiers d'extension .txt
 @Override
 public boolean accept( java.io.File f)
 {
	return f.getName().toLowerCase().endsWith(".txt");
 }
 @Override
 public String getDescription()
 {
	 return "(*.txt)";
 }
}
