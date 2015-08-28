package bot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

	private WebDriver driver;

	public MainFrame() {
		initComponents();
		initLayout();
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
		setMinimumSize(new Dimension(300, 500));
		pack();
		setLocationRelativeTo(MainFrame.this);
		setVisible(true);
	}

	private int getNumberOfElementsFound(By by) {
		return driver.findElements(by).size();
	}

	private WebElement getElementWithIndex(By by, int pos) {
		return driver.findElements(by).get(pos);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "startBot":
			driver = new FirefoxDriver();
			driver.get("https://www.1-day.co.nz/onsale/backyard280815/p/");
			int numberOfElementsFound = getNumberOfElementsFound(By
					.className("catalogue-product"));
			for (int pos = 0; pos < numberOfElementsFound; pos++) {
				WebElement productElement = getElementWithIndex(By.className("catalogue-product"), pos);
				productElement.findElement(By.className("image")).click();
				driver.findElement(By.id("buy_now")).click();
				driver.navigate().back();
				driver.navigate().back();
			}
			System.out.println("done");
			break;
		}
	}
}
