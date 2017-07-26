package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import java.util.ArrayList;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 * This class is used for development purpose only
 * It will load dummy data into Contact Database
 */

public class ContactDummyData {
    //region Global Variable
    private static final ArrayList<TypeContact> DummyContactList = new ArrayList<TypeContact>() {{
        add(new TypeContact("15555215554", "Ulfah Nadya", System.currentTimeMillis(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsmykZcBoklG/VOF6LZ7mswQCX5kQTwJ2MVL/fjw79iZb8HBT69P130HMlcALQUr5oA3GBvaOPBVGvvGvkCq2hxa9exSyTfVeDQhPDMUoZsnShzsX2VZpX0rkhTVlUFR3Rup7HS6zhlCdCCIgsOEvYCDpDt5++xPTplBytAH3TgXtzd56u2nkDy60b7CPCmZuEfJgFcSwzTx7wcm5pAOml1JDQuupPlG70TbdPxAlJmUSXVLt+8R5RTRVzF3e7y1LKXLH+qL0w3rcG5TC0anl314Vo7+yb24ImgdP7BdQrZz8E089OHlC1ry1nUTfngONG2nkdRG5oKhKnV6wva0CVwIDAQAB"));
        add(new TypeContact("15555215554", "Ulfah Nadya", System.currentTimeMillis(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsmykZcBoklG/VOF6LZ7mswQCX5kQTwJ2MVL/fjw79iZb8HBT69P130HMlcALQUr5oA3GBvaOPBVGvvGvkCq2hxa9exSyTfVeDQhPDMUoZsnShzsX2VZpX0rkhTVlUFR3Rup7HS6zhlCdCCIgsOEvYCDpDt5++xPTplBytAH3TgXtzd56u2nkDy60b7CPCmZuEfJgFcSwzTx7wcm5pAOml1JDQuupPlG70TbdPxAlJmUSXVLt+8R5RTRVzF3e7y1LKXLH+qL0w3rcG5TC0anl314Vo7+yb24ImgdP7BdQrZz8E089OHlC1ry1nUTfngONG2nkdRG5oKhKnV6wva0CVwIDAQAB"));
        add(new TypeContact("15555215556", "Fariz Azmi Pratama", System.currentTimeMillis(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwV3G508XV1ngOreOHpmIehz9hHbaijbmXxzD+QjC8jlP4W2P3IJbBTaKRHWkwk3GrqexSpQkmhBa4xaOOxld7vfn/yZNc0liVr4lnlaI9iTkQ9jarUnLlg0DKvmrgSUOslbSAtSZ2tszmIPeVZjIsuq//G+kWPCHXxo+bdEMGqiFraW7621polCwwTWzV11pmg+/fopHT6Jy1FYBH4RFrSNltCPIgAd2RmixwRWVEB9UNvx7f7eSY94bZptJar/khy+JGDHKgO3An2gMoXlUDLZ3RzE0iJ51yo0h8nGi8d2B0e72WWTNrRIb9IPIzgv8UOLtEU44OekLOMvj46TNgQIDAQAB"));
        add(new TypeContact("15555215558", "Bagus Hanindhito", System.currentTimeMillis(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8XV9rur7H1ONM1syNWm75aVLWiDUVh/y2XN80IfumhEvjV5x8GlcjNrhdWfL4vii9+4bKaUKNAr50Cw1XN8sGyb8bgVAXLLVIquZO8wB1h81PTiUNtoUA/Keb7jOwJXC2+pSW/+U1FpergC6PndS0JyjlLnxifMhudHwnL6mvGomr9x6lLlqTp1Ce0qctOV5cIHOqcYyQ/xjl8/sdXi1u8q8i01+XAIqg11YRyI1qouBYXkhHyzd4GhINy5p8jzttb12ugD6r0H5nU9ZDG3U/FCz9+/Nr9/wmkiOE30Rzw6+3RvcetFcEi+OesWl3b7QyVx4KrIZ1cxYX3j3O9SEFQIDAQAB"));
//        add(new TypeContact("15555215556", "Cindy Agustina", System.currentTimeMillis(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8lOF1TyPqcV8MOVf5r9yupNfYuhj7HP4rXuMARh/7D+4Z7kXQ9wiczoDvEg1wS8aoC8qKr5EbTy6X6BuUF4OCdVVNAZ3XOioqB2Hck8DlqTXDulEPDgfPOX45NxxNQZaK8KHcdORdq2pHIdPdo1mm7+SqUNr9d+ImlRDvLbHeTHOV2Bed693l9108P5LobacYdQSRnV5fZ7plO1qfthoxYB/56Lk6H4OHak8+Kjc5AAr7W3Dt0VFn0NALTY8QqvhaJ6c0VBi2K0GxvTj4sZu4KoJIY4AT3K9s82ZJN4Gqiwii893SMXR1H9m4UrKZafHHJeovj3+v6o4J65PKjltCwIDAQAB"));
//        add(new TypeContact("+6281395141700", "Bagus Hanindhito DEVICE", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("089203040120", "Oryza Sativa", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("085320456179", "Cannabis Sativa", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("087482813212", "Aurelia Auritania", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("082145875521", "Fitria Ridayanti", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("087545221214", "Wisnu Wijayanto", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("025871422552", "Netizen Budiman", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("025471421414", "Ulfah Nadya", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("593254411241", "Fariz Azmi", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("025478525224", "Mantan Terindah", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("025874568545", "Dia Yang Tersakiti", System.currentTimeMillis(), "00000000"));
//        add(new TypeContact("036587855457", "Maung Bandung", System.currentTimeMillis(), "00000000"));
    }};

    //endregion
    //region Static Methods
    public static void LoadDummyData(Context contex) {

        contactRepository contactRepo = new contactRepository(contex);
        for (int i = 0; i < DummyContactList.size(); i++) {
            contactRepo.insert(DummyContactList.get(i));
        }
    }

    public static void LoadSingleDummyData(Context contex, int idx) {

        contactRepository contactRepo = new contactRepository(contex);
        contactRepo.insert(DummyContactList.get(idx));
    }


    public static void ClearDB(Context context) {
        contactRepository contactRepo = new contactRepository(context);
        contactRepo.DropTable();
    }

    public static void CreateDB(Context context) {
        contactRepository contactRepo = new contactRepository(context);
        contactRepo.CreateTable();
    }
    //endregion
}
