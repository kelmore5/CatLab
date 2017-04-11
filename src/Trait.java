/**
 * <pre class="doc_header">
 * Class for defining representing dominant or recessive alleles for chickens
 * </pre>
 *
 * @author kelmore5
 * @custom.date Spring 2012
 */
class Trait
{
  private boolean a1, a2;
  private String dominant, recessive;

    /**
     * Instantiates a new Trait with alleles for dominance or recessiveness
     *
     * @param _a1        the allele from parent 1
     * @param _a2        the allele from parent 2
     * @param phenotypes a String array of the possible phenotypes (dominant and recessive)
     */
    Trait(boolean _a1, boolean _a2, String[] phenotypes) {
        a1 = _a1;
        a2 = _a2;
        dominant = phenotypes[0];
        recessive = phenotypes[1];
    }

    /**
     * Instantiates a new Trait using arrays
     *
     * @param genotype   the array of allele booleans
     * @param phenotypes the phenotypes
     */
    Trait(boolean[] genotype, String[] phenotypes) {
	  this(genotype[0], genotype[1], phenotypes);
  }

    /**
     * Gets the first allele
     *
     * @return the allele
     */
    boolean getA1()
  {
    return a1;
  }

    /**
     * Gets the second allele
     *
     * @return the second allele
     */
    boolean getA2()
  {
    return a2;
  }

    /**
     * Returns whether this trait is a dominant trait or recessive<br />
     * If dominant, trait will be displayed with just one allele being set to true<br />
     * If recessive, trait will only be displayed if both alleles are set to false
     *
     * @param _a1 the first allele
     * @param _a2 the second allele
     * @return true if one or both alleles are true (a1 & a2), false otherwise
     */
    private static boolean isDominant(boolean _a1, boolean _a2) {
	  return _a1 || _a2;
     }

    /**
     * Gets the phenotype for this trait
     *
     * @return The dominant phenotype if one or both alleles are true, recessive otherwise
     */
    private String getPhenotype() {
	  if(isDominant(a1, a2)) { return dominant; }
	  else { return recessive; }
  }

  public String toString() {
	  return getPhenotype();
  }
  
}
