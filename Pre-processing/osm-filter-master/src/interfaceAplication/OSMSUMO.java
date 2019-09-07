package interfaceAplication;





import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.JTextArea;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import classAplication.parserOSM;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import org.xml.sax.SAXException;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class OSMSUMO extends JFrame {

	parserOSM pOSM;
	JFileChooser fc;
	JTextArea log;
	JProgressBar progressBar;

	JLabel lblProgressBar;
	static private final String newline = "\n";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					OSMSUMO frame = new OSMSUMO();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public OSMSUMO() throws Exception {
		setResizable(false);

		pOSM = new parserOSM();

		pOSM.setPtrClassGui( this );

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 878, 562);

		fc = new JFileChooser();
		log = new JTextArea(5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);

		//////////////////////////// CheckBoxs  

		final JCheckBox chckbxxResidential = new JCheckBox("Residential");

		chckbxxResidential.setBounds(29, 122, 129, 23);


		final JCheckBox chckbxTertiary = new JCheckBox("Tertiary");

		chckbxTertiary.setBounds(173, 96, 129, 23);

		getContentPane().setLayout(null);
		getContentPane().add(chckbxxResidential);
		getContentPane().add(chckbxTertiary);

		JLabel lblWay = new JLabel("Include folllowing types of ways:");
		lblWay.setBounds(29, 49, 386, 14);
		getContentPane().add(lblWay);

		JLabel lblParserDeArquivos = new JLabel("OSM Filter");
		lblParserDeArquivos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblParserDeArquivos.setBounds(284, 11, 199, 14);
		getContentPane().add(lblParserDeArquivos);

		final JCheckBox chckbxMotorway = new JCheckBox("Motorway");
		chckbxMotorway.setBounds(29, 70, 129, 23);
		getContentPane().add(chckbxMotorway);

		final JCheckBox chckbxPrimary = new JCheckBox("Primary");
		chckbxPrimary.setBounds(304, 70, 129, 23);
		getContentPane().add(chckbxPrimary);

		final JCheckBox chckbxSecondary = new JCheckBox("Secondary");
		chckbxSecondary.setBounds(29, 96, 129, 23);
		getContentPane().add(chckbxSecondary);

		final JCheckBox chckbxTrunk = new JCheckBox("Trunk");
		chckbxTrunk.setBounds(173, 70, 129, 23);
		getContentPane().add(chckbxTrunk);

		final JCheckBox chckbxUnclassified = new JCheckBox("Unclassified");
		chckbxUnclassified.setBounds(304, 96, 142, 23);
		getContentPane().add(chckbxUnclassified);

		final JCheckBox chckbxRoad = new JCheckBox("Road");
		chckbxRoad.setBounds(304, 122, 124, 23);
		getContentPane().add(chckbxRoad);

		final JCheckBox chckbxLivingStreet = new JCheckBox("Living Street");
		chckbxLivingStreet.setBounds(171, 122, 142, 23);
		getContentPane().add(chckbxLivingStreet);

		final JCheckBox chckbxTransformaEmReta = new JCheckBox("Remove Geometry");
		chckbxTransformaEmReta.setBounds(586, 70, 280, 23);
		getContentPane().add(chckbxTransformaEmReta);

		final JCheckBox chckbxWindows = new JCheckBox("Windows");
		chckbxWindows.setBounds(489, 21, 124, 23);
		getContentPane().add(chckbxWindows);

		JCheckBox chckbxLinux = new JCheckBox("Linux");
		chckbxLinux.setBounds(634, 21, 98, 23);
		getContentPane().add(chckbxLinux);
		/***************************************************************
		 * 
		 * 							Build file
		 * 
		 ***************************************************************/	


		JButton btnGerarArquivo = new JButton("Build file");


		btnGerarArquivo.addActionListener(new ActionListener() {



			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> listaParaManter = new ArrayList<String>();

				if(chckbxxResidential.isSelected() == true){

					listaParaManter.add("residential");
					//pOSM.remove("residential");

					System.out.println("way  (residential) keeping the file! ");

				}

				if(chckbxMotorway.isSelected() == true){

					listaParaManter.add("motorway");
					listaParaManter.add("motorway_link");
					System.out.println("way  (motorway) keeping the file! ");

				}

				if(chckbxPrimary.isSelected() == true){


					listaParaManter.add("primary");
					listaParaManter.add("primary_link");
					System.out.println("way  (primary) keeping the file! ");
				}




				if(chckbxSecondary.isSelected()== true){


					listaParaManter.add("secondary");
					listaParaManter.add("secondary_link");
					System.out.println("way  (secondary) keeping the file!");
				}

				if(chckbxTertiary.isSelected()== true){



					listaParaManter.add("tertiary");
					System.out.println("way  (tertiary)  keeping the file!");

				}

				if(chckbxTrunk.isSelected()== true){


					listaParaManter.add("trunk");
					listaParaManter.add("trunk_link");
					System.out.println("way  (trunk) keeping the file!");
				}

				if(chckbxUnclassified.isSelected()== true){


					listaParaManter.add("unclassified");
					System.out.println("way  (unclassified) keeping the file!");
				}

				if(chckbxRoad.isSelected()== true){

					listaParaManter.add("road");
					System.out.println("way  (road) keeping the file");

				}

				if(chckbxLivingStreet.isSelected()== true){


					listaParaManter.add("living_street");
					System.out.println("way  (living_street) keeping the file! ");
				}


				try {
					pOSM.remove(listaParaManter);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pOSM.preencheHashMap();
				if(chckbxTransformaEmReta.isSelected()){

					pOSM.ExcluirNodosIntermediarios();


				}        	 

				pOSM.removeNodeOrfaos();





				int returnVal = fc.showSaveDialog(OSMSUMO.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					//This is where a real application would save the file.
					log.append("Saving: " + file.getName() + "." + newline);
					try {
						pOSM.GeraNovoArquivo(file.getAbsolutePath(), file.getName() );

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					log.append("Save command cancelled by user." + newline);
				}
				log.setCaretPosition(log.getDocument().getLength());

				//check SO

				if(chckbxWindows.isSelected()){


					try {
						pOSM.chamaConsoleWindows();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


			}
		}
		);



		btnGerarArquivo.setBounds(439, 122, 142, 23);
		getContentPane().add(btnGerarArquivo);

		progressBar = new JProgressBar();
		progressBar.setBounds(558, 468, 232, 14);
		getContentPane().add(progressBar);

		lblProgressBar = new JLabel("Status");
		lblProgressBar.setBounds(558, 443, 233, 14);
		getContentPane().add(lblProgressBar);

		/***************************************************************
		 * 
		 * 							Open file
		 * 
		 ***************************************************************/	

		JButton btnAbriArquivo = new JButton("Open file");
		btnAbriArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int returnVal = fc.showOpenDialog(OSMSUMO.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//This is where a real application would open the file.
					log.append("Opening: " + file.getName() + "." + newline);
					try {

						file.getAbsolutePath();

						pOSM.abrirArquivo(file.getAbsolutePath(), file.getName());

					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					log.append("Open command cancelled by user." + newline);
				}
				log.setCaretPosition(log.getDocument().getLength());



			}
		});
		btnAbriArquivo.setBounds(439, 70, 135, 23);
		getContentPane().add(btnAbriArquivo);

		JLabel lblInformaes = new JLabel("Information for next steps in Windows:");
		lblInformaes.setBounds(29, 162, 423, 14);
		getContentPane().add(lblInformaes);

		JLabel lblNewLabel = new JLabel("2. In the  prompt type the comand: netconvert --osm SavedFile.osm.xml");
		lblNewLabel.setBounds(29, 212, 621, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblSerGeradoUm = new JLabel("4. The file will be created in the folder of project with name : net.net.xml ");
		lblSerGeradoUm.setBounds(29, 415, 646, 14);
		getContentPane().add(lblSerGeradoUm);

		JLabel lblBastaCarregarEsse = new JLabel("4. Load this file in SUMO");
		lblBastaCarregarEsse.setBounds(29, 262, 415, 14);
		getContentPane().add(lblBastaCarregarEsse);

		JLabel lblSalveOArquivo = new JLabel("1. Save file with extension .osm.xml");
		lblSalveOArquivo.setBounds(29, 187, 390, 14);
		getContentPane().add(lblSalveOArquivo);



		JLabel lblInformaesLinux = new JLabel("Information for next steps in Linux:");
		lblInformaesLinux.setBounds(29, 308, 423, 14);
		getContentPane().add(lblInformaesLinux);

		JLabel lblDepoisDeSalvo = new JLabel("2. Open the console and go the folder of project");
		lblDepoisDeSalvo.setBounds(29, 365, 506, 14);
		getContentPane().add(lblDepoisDeSalvo);

		JLabel lblSerGerado = new JLabel("3. The file will be created in the folder of project with name : net.net.xml ");
		lblSerGerado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		lblSerGerado.setBounds(29, 237, 621, 14);
		getContentPane().add(lblSerGerado);

		JLabel lbluploadThisFile = new JLabel("5. Upload this file in SUMO");
		lbluploadThisFile.setBounds(29, 440, 415, 14);
		getContentPane().add(lbluploadThisFile);

		JLabel lblSaveFile = new JLabel("1. Save file with extension .osm.xml");
		lblSaveFile.setBounds(29, 340, 390, 14);
		getContentPane().add(lblSaveFile);

		JLabel lblInThe = new JLabel("3. In the  prompt type the comand: netconvert --osm SavedFile.osm.xml");
		lblInThe.setBounds(29, 390, 621, 14);
		getContentPane().add(lblInThe);


	}


	public void setLabelProgress(String newLabel){

		lblProgressBar.setText(newLabel);

		Rectangle labelRect = lblProgressBar.getBounds();
		labelRect.x = 0;
		labelRect.y = 0;
		lblProgressBar.paintImmediately( labelRect );

	}


	public void setMaxProgressBar(int max){
		progressBar.setMaximum(max);
	}



	public void setPositionProgressBar(int pos){
		progressBar.setValue(pos);

		Rectangle progressRect = progressBar.getBounds();

		progressRect.x = 0;
		progressRect.y = 0;

		progressBar.paintImmediately( progressRect );


	}
}
