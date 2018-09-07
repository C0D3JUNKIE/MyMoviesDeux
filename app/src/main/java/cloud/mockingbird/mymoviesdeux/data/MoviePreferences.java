package cloud.mockingbird.mymoviesdeux.data;

/**
 * Simple class for holding class vars for sorting.
 */
public class MoviePreferences {

    public static final String PREF_SORT_POPULARITY = "popular";
    public static final String PREF_SORT_RATING = "top_rated";
    public static final String DEFAULT_SORT = PREF_SORT_POPULARITY;


    /**
     * Get method for returning a specified sort.
     *
     * @return either popular or top_rated.
     */
    public static String getSortPreferred() {
        return DEFAULT_SORT;
    }

}
