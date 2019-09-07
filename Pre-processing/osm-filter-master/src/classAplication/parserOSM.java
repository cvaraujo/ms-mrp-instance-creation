package classAplication;

import interfaceAplication.OSMSUMO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class parserOSM {

	//Lists to store the xml structures
	NodeList listOfNodes;
	NodeList listOfWay;
	NodeList listOfRef;
	Document doc;
	DocumentBuilder docBuilder;
	DocumentBuilderFactory docBuilderFactory;
	HashMap<String, Integer> meuMapa;
	OSMSUMO ptrClassGui;

	public parserOSM() throws Exception {

		meuMapa = new HashMap<String, Integer>();
	}

	public void abrirArquivo(String pathArq, String nome)
	throws ParserConfigurationException, SAXException, IOException {

		System.out.println("path of file is open: " + pathArq);

		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();

		doc = docBuilder.parse(new File(pathArq)); //Create an xml document based on the loaded
		doc.getDocumentElement().normalize();
		// System.out.println ("Root element of the doc is " +
		// doc.getDocumentElement().getNodeName()); //print the name of document
		System.out.println("Arquivo Aberto: " + nome);

		listOfNodes = doc.getElementsByTagName("node");
		listOfWay = doc.getElementsByTagName("way");

		int totalNodos = listOfNodes.getLength();

		System.out.println("Total of nodes file: " + totalNodos);

	}

	public void setPtrClassGui(OSMSUMO ptr) {

		ptrClassGui = ptr;

	}

	/******************************************************************************************
	 * 
	 * 							Method that calls the console wiondows
	 * 
	 * ****************************************************************************************/
	public void chamaConsoleWindows() throws IOException {

		String[] comandoComParametros = new String[] { "cmd.exe", "/C", "start"};
		Runtime.getRuntime().exec(comandoComParametros);

	}

	/******************************************************************************************
	 * 
	 * 							Method that build the new filtred file 
	 * 
	 * ****************************************************************************************/
	public void GeraNovoArquivo(String pathArq, String nomeArq)
	throws TransformerException, IOException {

		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(pathArq);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();

		transformer.transform(source, result);


		ptrClassGui.setLabelProgress("Saved file.");

	}
	/******************************************************************************************
	 * 
	 * 							Method that remove geometry of file  
	 * 
	 * ****************************************************************************************/


	public void ExcluirNodosIntermediarios() {

		Node ptrNodo = null;
		Node ptrNodoUltimo = null;
		Node nodeAux;

		NodeList listOfChild;

		if (listOfWay.getLength() > 2) {

			//through the way list 
			for (int w = 0; w < listOfWay.getLength(); w++) {

				listOfChild = listOfWay.item(w).getChildNodes();

				int t = listOfChild.getLength() - 2;

				while (t > 0) {
					// System.out.println( listOfChild.item(t).getNodeName());
					if (listOfChild.item(t).getNodeName().equals("nd") == true) {
						ptrNodoUltimo = listOfChild.item(t);
						break;
					}
					t -= 2;

				}

				ptrNodo = listOfChild.item(3);

				while (((Element) ptrNodo).getAttribute("ref").toString() != ((Element) ptrNodoUltimo)
						.getAttribute("ref").toString()) {

					if ((meuMapa.containsKey(((Element) ptrNodo).getAttribute("ref"))) && (meuMapa.get(((Element) ptrNodo).getAttribute("ref")) < 2)) {

						meuMapa.put(((Element) ptrNodo).getAttribute("ref"), 0);

						nodeAux = ptrNodo.getNextSibling();
						nodeAux = nodeAux.getNextSibling();
						ptrNodo.getParentNode().removeChild(ptrNodo);
						ptrNodo = nodeAux;

					} else {
						ptrNodo = ptrNodo.getNextSibling();
						ptrNodo = ptrNodo.getNextSibling();
					}

					if (((Element) ptrNodo) == null)
						break;

					// System.out.println( ptrNodo.getNodeName());
				}
			}
		}

	}

	/******************************************************************************************
	 * 
	 * 							Method that keeps crossing 
	 * 
	 * ****************************************************************************************/
	public void preencheHashMap() {

		String IdNode;
		NodeList listOfChildWay;


		ptrClassGui.setPositionProgressBar(0);
		ptrClassGui.setMaxProgressBar(listOfNodes.getLength());

		ptrClassGui.setLabelProgress("Fill HashMap... ");
		int a =0;
		// zero in the hash
		for (int i = 0; i < listOfNodes.getLength(); i++) {
			ptrClassGui.setPositionProgressBar(a);
			a++;

			IdNode = ((Element) listOfNodes.item(i)).getAttribute("id");

			meuMapa.put(IdNode, 0);
		}

		String ref;

		for (int w = 0; w < listOfWay.getLength(); w++) {

			listOfChildWay = listOfWay.item(w).getChildNodes();// daughters ways : tag and nd								// de way tag,


			for (int i = 0; i < listOfChildWay.getLength(); i++) {

				if (listOfChildWay.item(i).getNodeName().equals("nd")) {

					ref = ((Element) listOfChildWay.item(i))
					.getAttribute("ref");

					meuMapa.put(ref, meuMapa.get(ref) + 1);

				}

			}
		}

	}

	/******************************************************************************************
	 * 
	 * 							Method that remove orphaned nodes
	 * 
	 * ****************************************************************************************/

	public void removeNodeOrfaos() {

		Node ptrNodo;
		Node ptrlistOfChild;
		NodeList listOfChild;
		boolean EparaExcluirNode = true;

		ptrClassGui.setPositionProgressBar(0);

		ptrClassGui.setMaxProgressBar(listOfNodes.getLength());

		ptrClassGui.setLabelProgress("Remove orphaned nodes ");

		System.out.println("Remove orphaned nodes....");

		//through the node list 
		int a = 0;

		for (int n = 0; n < listOfNodes.getLength(); n++) {

			ptrClassGui.setPositionProgressBar(a);
			a++;

			EparaExcluirNode = true;
			ptrNodo = listOfNodes.item(n);

			//through the way list 
			for (int w = 0; w < listOfWay.getLength(); w++) {

				if (EparaExcluirNode == true) {

					listOfChild = listOfWay.item(w).getChildNodes();

					//through the daughters ways 
					for (int t = 0; t < listOfChild.getLength(); t++) {

						if (EparaExcluirNode == true) {

							ptrlistOfChild = listOfChild.item(t);

							//checks if tag is = nd
							if (ptrlistOfChild.getNodeName().equals("nd")) {

								//checks if node id = ref (way) 
								if (((Element) ptrNodo).getAttribute("id")
										.equals(
												(((Element) ptrlistOfChild)
														.getAttribute("ref")))) {
									EparaExcluirNode = false;
								}

							}
						}

					}
				}
			}

			if (EparaExcluirNode == true) {

				listOfNodes.item(n).getParentNode().removeChild(
						listOfNodes.item(n));

				n = n - 1;

				if (n < 0)
					n = 0;

			}
		}

		System.out.println("Remove orphaned nodes!");
		int totalNodosVovoArquivo = listOfNodes.getLength();
		System.out.println("Total of nodes in the new file: "
				+ totalNodosVovoArquivo);

	}

	/******************************************************************************************
	 * 
	 * 							Method that remove way
	 * 
	 * ****************************************************************************************/
	public void remove(ArrayList<String> ListaParaFicar) throws IOException,
	TransformerException {

		NodeList listOfChild;
		Node ptrNodo;

		ptrClassGui.setMaxProgressBar(listOfWay.getLength());

		ptrClassGui.setLabelProgress("Remove Ways...");

		boolean ehParaRemover;

		int a = 0;

		for (int w = 0; w < listOfWay.getLength(); w++) {

			ptrClassGui.setPositionProgressBar(a);

			a++;

			listOfChild = listOfWay.item(w).getChildNodes();

			ehParaRemover = true;

			for (int t = 0; t < listOfChild.getLength(); t++) {

				ptrNodo = listOfChild.item(t);

				if (ptrNodo.getNodeName().equals("tag")) {

					String nomeWay = ((Element) ptrNodo).getAttribute("v")
					.toString();

					if (ListaParaFicar.contains(nomeWay) == true) {

						ehParaRemover = false;


						break;

					}

				}

			}

			if (ehParaRemover == true) {

				listOfWay.item(w).getParentNode()
				.removeChild(listOfWay.item(w));

				w = w - 1;

				if (w < 0)
					w = 0;
			}

		}

	}

}
