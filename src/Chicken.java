/**
 * <pre class="doc_header">
 * A class to represent a chicken. Includes options for egg shell color, type of legs,
 * whether the chicken is a parent or not and if it is male or female.
 * </pre>
 *
 * @author kelmore5
 * @custom.date Spring 2012
 */
class Chicken {
	//Defining class variables for options of chicken
	private Trait eggShell, legs;
	private boolean isParent, isMale;

	/**
	 * Instantiates a new Chicken
	 *
	 * @param _isMale   true if male, false if female
	 * @param _eggShell the type of egg shell (blue or orange)
	 * @param _legs     the type of legs (creeper or normal)
	 */
	Chicken(boolean _isMale, Trait _eggShell, Trait _legs) {
		//Set defaults
		isMale = _isMale;
		eggShell = _eggShell;
		legs = _legs;
		isParent = false;
	}

	/**
	 * Instantiates a new Chicken.
	 *
	 * @param gender    the gender of the chicken (string representation)
	 * @param _eggShell the egg shell color
	 * @param _legs     the type of leg
	 */
	Chicken(String gender, Trait _eggShell, Trait _legs) {
		//Set defaults
		this(true, _eggShell, _legs);

		//If female, change gender
		if(gender.equals("Female")) {
			isMale = false;
		}
	}

	/**
	 * Gets the gender of the chicken. Returns a string representation rather than a boolean
	 *
	 * @return the gender of the chicken
	 */
	String getGender() {
		if(isMale) { return "Male"; }
		else { return "Female"; }
	}

	/**
	 * Checks whether the chicken is male or not
	 *
	 * @return true if chicken is male, false otherwise
	 */
	boolean isMale() {
		return isMale;
	}

	/**
	 * Gets the egg shell trait<br />
	 * Color can either be blue or orange
	 *
	 * @return the egg shell trait
	 */
	Trait getEggShell() {
		return eggShell;
	}

	/**
	 * Get the type of legs for the chicken<br />
	 * Can either by creeper (a lethal allele) or normal
	 *
	 * @return the legs trait
	 */
	Trait getLegs() {
		return legs;
	}

	/**
	 * Sets whether the chicken is a parent or not<br />
	 * Used to determine which chickens in the program breed
	 *
	 * @param parent true if chicken should be parent, false otherwise
	 */
	public void setParent(boolean parent) {
		isParent = parent;
	}

	/**
	 * Checks if the chicken is a parent or not
	 *
	 * @return true if chicken is parent, false otherwise
	 */
	public boolean isParent()
	{
		return isParent;
	}

}
