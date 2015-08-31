package bot;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CartPanel extends JPanel {

	private JList<String> cart;
	private DefaultListModel<String> cartItemList;

	public CartPanel() {
		cartItemList = new DefaultListModel<String>();
		cart = new JList<String>(cartItemList);
		add(new JScrollPane(cart));
	}
	
	public void addItem(String item){
		cartItemList.addElement(item);
	}

	public void addItemList(List<String> boughtItems) {
		for(String item : boughtItems){
			cartItemList.addElement(item);
		}
	}
}
