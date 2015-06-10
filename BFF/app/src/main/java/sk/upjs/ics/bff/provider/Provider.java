package sk.upjs.ics.bff.provider;

import android.provider.BaseColumns;

/**
 * Created by Jana on 4.6.2015.
 */
public interface Provider {

    public interface user extends BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String NAME = "name";

        public static final String WEIGHT = "weight";

        public static final String BFF_NUMBER = "bff_number";

    }

    public interface alcohol extends BaseColumns {

        public static final String TABLE_NAME = "alcohol";

        public static final String NAME = "name";

        public static final String VOLUME = "volume";

        public static final String COST = "cost";


    }

    public interface statistics extends BaseColumns {

        public static final String TABLE_NAME = "statistics";

        public static final String TIMESTAMP = "timestamp";

        public static final String ID_ALCOHOL = "id_alcohol";

    }

}
