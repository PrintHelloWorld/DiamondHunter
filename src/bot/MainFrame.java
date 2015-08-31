package bot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private JButton startBotButton;
	private CartPanel cartPanel;

	private static final String TITLE = "Diamond Deal Bot";

	private List<String> priorityList;

	private WebDriver driver;

	public MainFrame() {
		initComponents();
		initLayout();
		priorityList = loadPriorityList();
		cartPanel.addItem("Cart Item List");
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		startBotButton = new JButton("Start Hunting");
		startBotButton.addActionListener(this);
		startBotButton.setActionCommand("startBot");
		cartPanel = new CartPanel();
		add(cartPanel, BorderLayout.CENTER);
		add(startBotButton, BorderLayout.SOUTH);
	}

	private void initLayout() {
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300, 300));
		pack();
		setLocationRelativeTo(MainFrame.this);
		setVisible(true);
	}

	private List<String> loadPriorityList() {
		List<String> list = new ArrayList<String>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("prioritylist.txt"));
			while (sc.hasNext()) {
				list.add(sc.next());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		return list;
	}

	private int getNumberOfElementsFound(By by) {
		return driver.findElements(by).size();
	}

	private WebElement getElementWithIndex(By by, int pos) {
		return driver.findElements(by).get(pos);
	}

	private boolean buyProduct(WebElement productElement) {
		productElement.findElement(By.className("image")).click();
		if (driver.findElement(By.id("buy_now")) != null) {
			driver.findElement(By.id("buy_now")).click();
			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "startBot":
			driver = new FirefoxDriver();
			/* TODO Prompt site input | auto search for diamond deal link */
			driver.get("https://www.1-day.co.nz/onsale/smartphone310815/p/");

			List<String> boughtItems = new ArrayList<String>();

			int numberOfElementsFound = getNumberOfElementsFound(By
					.className("catalogue-product"));
			for (String brand : priorityList) {
				for (int pos = 0; pos < numberOfElementsFound; pos++) {
					WebElement productElement = getElementWithIndex(
							By.className("catalogue-product"), pos);
					/*
					 * find product elements matching priority list in order of
					 * buy preference
					 */
					String productTitle = productElement
							.findElement(By.className("title")).getText()
							.toLowerCase();
					if (productTitle.contains(brand.toLowerCase())) {
						System.out.println("Found product " + productTitle);
						if (buyProduct(productElement)) {
							boughtItems.add(productTitle);
						}
						driver.get("https://www.1-day.co.nz/onsale/smartphone310815/p/");
					}
				}
			}
			driver.close();
			cartPanel.addItemList(boughtItems);
			System.out.println("done");
			break;
		}
	}
}
