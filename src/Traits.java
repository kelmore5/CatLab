/**
 * <pre class="doc_header">
 * Class for defining traits of chickens. Only consists of String arrays.
 * Each string array has the structure: Dominant phenotype, recessive phenotype,
 * if the dominant trait is lethal, and if the recessive trait is lethal
 * </pre>
 *
 * @author kelmore5
 * @custom.date Spring 2012
 */
final class Traits {
	//Strings in order = [Dominant phenotype, recessive phenotype, if lethal for dominant, if lethal for recessive]

	/**
	 * The constant egg color array. Contains strings: blue, not blue, false, false
	 */
	final static String[] eggColor = new String[] {"Blue", "Not blue", "false", "false"};
	/**
	 * The constant type of legs. Contains: creeper, normal, true, false
	 */
	final static String[] legs = new String[] {"Creeper", "Normal", "true", "false"};
}
