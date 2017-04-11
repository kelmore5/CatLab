import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * <pre class="doc_header">
 * An extension of JPanel designed for the user's interaction.
 * Displays the results of breeding multiple chickens (e.g. alleles, traits, and genders of all offspring).
 * </pre>
 *
 * @author kelmore5
 * @custom.date Spring 2012
 */
class CrossPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;									//The main display
	private ArrayList<Chicken> chickens;					//Array for chicken offspring
	private int currentMaleParent, currentFemaleParent;		//The current chickens being used for breeding
	private Random r;										//Random number generator for creating alleles
	private int chickenNum;									//The number of chickens currently created
	@SuppressWarnings("FieldCanBeLocal")
	private final String[] columnNames = {					//Table column names
			"#",
			"Ltr",
			"Parents",
			"Sex",
			"Egg Shell Color",
	"Legs"};

	/**
	 * Instantiates a new Cross panel.
	 */
	CrossPanel() {
		//Initialize variables
		chickenNum = 0;												//Set chickens to zero
		currentMaleParent = -1;										//No assigned male/female - set both to -1
		currentFemaleParent = -1;
		chickens = new ArrayList<>();								//Array list
		r = new Random();											//Random nums
		table = new JTable(new MyModel(columnNames, 0));		//New table with column names
		this.add(new JScrollPane(table));							//Add table to a scroll panel and frame
	}

	/**
	 * Sets the parents for the chickens
	 */
	void setParents() {
		//Get the seleced rows for the chickens
		int[] rows = table.getSelectedRows();

		//If rows is greater than 2, return error message
		if(rows.length > 2) {
			JOptionPane.showMessageDialog(this, "Please select a maximum of two chickens to be parents");
			return;
		}
		//If no rows selected, return error message
		else if(rows.length < 1) {
			JOptionPane.showMessageDialog(this, "You have not selected any chickens to be a parent");
		}

		//Make the selected chickens parents
		parentfyChicken(chickens.get(rows[0]), rows[0]);

		//If two chickens selected, parentify them as well
		if(rows.length == 2) {
			parentfyChicken(chickens.get(rows[1]), rows[1]);
		}
	}

	/**
	 * Takes a chicken and the row number and then makes that chicken a parent
	 * @param chicken 	The chicken to be changed
	 * @param row		The row number
	 */
	private void parentfyChicken(Chicken chicken, int row) {
		//Set parent to true
		chicken.setParent(true);

		//If male, set the current male parent variable to the correct row
		//Otherwise, set the female variable
		if(chicken.isMale()) {
			if(currentMaleParent > 0) {
				chickens.get(currentMaleParent).setParent(false);
				table.setValueAt("--", currentMaleParent, 2);
			}
			currentMaleParent = row;
		}
		else {
			if(currentFemaleParent > 0) {
				chickens.get(currentFemaleParent).setParent(false);
				table.setValueAt("--", currentFemaleParent, 2);
			}
			currentFemaleParent = row;
		}
		table.setValueAt("Parent", row, 2);
	}

	/**
	 * Creates a chicken by acquiring user input
	 * Gets the gender, egg shell color, and leg type
	 */
	void createChicken() {
		//Get gender from JOptionPane and return if null
		Object gender = JOptionPane.showInputDialog(null, "Choose whether your chicken is male or female:\n", "Gender", 
				JOptionPane.INFORMATION_MESSAGE, null, 
				new String[] {"Male", "Female"}, "Male");

		if(gender == null) {
			return;
		}

		//Get egg color from JOptionPane and return if null
		Object eggColor = JOptionPane.showInputDialog(null, "Choose your chicken's egg shell color\n", "Egg Shell", 
				JOptionPane.INFORMATION_MESSAGE, null, 
				new String[] {"Blue", "Not blue"}, "Not blue");
		
		if(eggColor == null) {
			return;
		}

		//Get legs from JOptionPane and return if null
		Object legs = JOptionPane.showInputDialog(null, "Choose your chicken's legs:\n", "Legs", 
				JOptionPane.INFORMATION_MESSAGE, null, 
				new String[] {"Normal", "Creeper"}, "Normal");
		
		if(legs == null) {
			return;
		}

		//Create the chicken and add it to the table
		Chicken chicken = new Chicken(gender.toString(), processTrait(eggColor.toString(), Traits.eggColor), processTrait(legs.toString(), Traits.legs));
		chickens.add(chicken);
		chickenNum++;
		((MyModel) table.getModel()).addRow(new String[] {"" + chickenNum, "--", "--", chicken.getGender(), chicken.getEggShell().toString(), chicken.getLegs().toString()});
	}

	/**
	 * Process whether a trait is dominant or recessive
	 * @param phenotype	the phenotype being tested
	 * @param traits	the traits
	 * @return			The singular trait/genotype being displayed in the chicken
	 */
	private Trait processTrait(String phenotype, String[] traits) {
		//If dominant, generate a dominant genotype
		//Else, return a recessive genotype (two false boolean values for the alleles)
		if(phenotype.equals(traits[0])) {
			return new Trait(generateDomGenotype(Boolean.parseBoolean(traits[2])), traits);
		}
		else {
			return new Trait(false, false, traits);
		}
	}

	/**
	 * Create a dominant genotype based on lethality
	 * @param lethal	Whether gene is lethal or not
	 * @return			The genotype boolean array
	 */
	private boolean[] generateDomGenotype(boolean lethal) {
		//If lethal, create a true + false array (because two trues and the chicken would not be born).
		//Otherwise, create a true + random array
		if(lethal) {
			return new boolean[] {true, false};
		}
		else {
			return new boolean[] {true, r.nextBoolean()};
		}
	}

	/**
	 * Mates the chickens
	 *
	 * @param offspring the number of offspring to create
	 */
	void mateChickens(int offspring) {
		//Check if parent males are selected first. If not, return
		if(currentMaleParent < 0 || currentFemaleParent < 0) {
			JOptionPane.showMessageDialog(this, "You need to set the parents");
			return;
		}

		//Get the male and female chickens
		Chicken male = chickens.get(currentMaleParent);
		Chicken female = chickens.get(currentFemaleParent);

		//Loop through until all offspring are created
		for(int k = 0; k < offspring; k++)
		{
			//Create the legs trait first (to see if chicken lives or not)
			//If chicken gets two recessive leg alleles, it is not born, so skip
			Trait legs = getTrait(male.getLegs(), female.getLegs(), Traits.legs);
			if(!(legs.getA1() && legs.getA2())) {
				//If legs non-lethal, create chicken and add to array/table
				Chicken chicken = new Chicken(r.nextBoolean(), getTrait(male.getEggShell(), 
						female.getEggShell(), Traits.eggColor), legs);
				chickens.add(chicken);
				chickenNum++;
				((MyModel) table.getModel()).addRow(new String[] {"" + chickenNum, "--", "--", 
						chicken.getGender(), chicken.getEggShell().toString(), chicken.getLegs().toString()});
			}
		}
	}

	/**
	 * Create a trait from two traits
	 * @param male		//The male trait
	 * @param female	//The female trait
	 * @param traits	//Array of all possible options for the trait
	 * @return			//A new randomly generated trait
	 */
	private Trait getTrait(Trait male, Trait female, String[] traits) {
		//Randomly select alleles from both parents
		boolean newA1 = r.nextBoolean() ? male.getA1() : male.getA2();
		boolean newA2 = r.nextBoolean() ? female.getA1() : female.getA2();
		
		return new Trait(newA1, newA2, traits);
	}

	private class MyModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new My model
		 *
		 * @param columns the columns
		 * @param rows    the rows
		 */
		MyModel(Object[] columns, int rows) {
			super(columns, rows);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}
