package app.com.dunkeydelivery.utils;

/**
 * Created by Developer on 6/9/2017.
 * <p>
 * Common Keys for web-services, intents and sharedprefs.
 */
public class Keys {
    /**
     * Label of the element in json that contains the actual data returned from webservice.
     */
    public static final String DATA_ITEM_JSON_WEBSERVICE = "Result";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String ROLE = "role";

    public static final String CONFIRM_PASSWORD = "confirmpassword";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String FILE = "file";
    public static final String USER_ID = "user_id";
    public static final String USER_ID_1 = "UserID";
    public static final String USER_ID_2 = "UserId";
    public static final String USER_ID_3 = "User_Id";
    public static final String SETTINGS = "settings";


    /**
     * Keys used for passing params in webservice.
     */
    public static String Body = "body";

    public static final String EMAIL = "email";
    public static final String FULLNAME = "fullname";
    public static final String USER_EMAIL = "userEmail";
    public static final String TOKEN = "token";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";

    public static final String Address = "address";
    public static final String Components = "components";
    public static final String Key = "key";
    public static final String LatLng = "latlng";
    public static final String Category_id = "Category_Id";
    public static final String Lat = "Lat";
    public static final String Lng = "Lng";


    public static String Store_id = "Store_Id";
    public static String PARENT_CATEGORY_ID = "ParentCategory_id";
    public static String Page = "Page";
    public static String Items = "Items";
    public static String search_string = "search_string";
    public static String Category_Id = "Category_Id";
    public static String Category_Type = "Category_Type";

    //facebook
    public static String FB_Id = "userId";
    public static String FB_AccessToken = "accessToken";
    public static String FB_SocialLoginType = "socialLoginType";
    public static String GMAIL_SocialLoginType = "SignIntype";

    //setCardData
    public static String CCNo = "CCNo";
    public static String ExpiryDate = "ExpiryDate";
    public static String CCV = "CCV";
    public static String BillingCode = "BillingCode";
    public static String Is_Primary = "Is_Primary";
    public static String User_ID = "User_ID";
    public static String Label = "Label";

    //setAddressList
    public static String City = "City";
    public static String State = "State";
    public static String Telephone = "Telephone";
    public static String FullAddress = "FullAddress";
    public static String Address2 = "Address2";
    public static String Frequency = "Frequency";
    public static String PostalCode = "PostalCode";
    public static String IsDeleted = "IsDeleted";
    public static String IsPrimary = "IsPrimary";
    public static String id = "id";

    //deleteCreditCard
    public static String Card_Id = "Card_Id";
    public static String Authorization = "Authorization";

    //deleteAddressList
    public static String AddressId = "address_id";

    //rideInformation
    public static String BusinessName = "BusinessName";
    public static String BusinessType = "BusinessType";
    public static String ZipCode = "ZipCode";
    public static String SignInType = "SignInType";


    //cart
    public static final String IS_EXISTS = "isExists";
    public static final String POSITION = "position";

    public static final String DOCTOR_FIRST_NAME = "Doctor_FirstName";
    public static final String DOCTOR_LAST_NAME = "Doctor_LastName";
    public static final String DOCTOR_PHONE = "Doctor_Phone";
    public static final String PATIENT_FIRST_NAME = "Patient_FirstName";
    public static final String PATIENT_LAST_NAME = "Patient_LastName";
    public static final String PATIENT_DOB = "Patient_DOB";
    public static final String GENDER = "Gender";
    public static final String PRODUCT_IDS = "Product_Ids";
    public static final String PRODUCT_ID = "Product_Id";

    //setContactUs
    public static final String Name = "Name";
    public static final String Message = "Message";
    public static final String ContactReason = "ContactReason";
    public static final String Email = "Email";
    public static final String Phone = "Phone";
    public static final String Stores = "Store";
    public static final String OrderSummary = "OrderSummary";
    public static String Quantity = "Quantity";
    public static String products = "products";

    public static String IS_CURRENT_ORDER = "IsCurrentOrder";
    public static String PAGE_SIZE = "PageSize";
    public static String PAGE_NO = "PageNo";

    //setPoints
    public static String RewardID = "RewardID";

    //setAlcoholHomeScreen
    public static String latitude = "latitude";
    public static String longitude = "longitude";
    public static String Store_Ids = "Store_Ids";


    public static String CART = "Cart";
    public static String CART_ITEMS = "CartItems";
    public static String ADDITIONAL_NOTE = "AdditionalNote";
    public static String PAYMENT_METHOD_TYPE = "PaymentMethodType";
    public static String DELIVERY_ADDRESS = "DeliveryAddress";

    //setChangeStore
    public static String Type = "Type";

    //setAlcoholProducts
    public static String Category_ParentId = "Category_ParentId";
    public static String CategoryName = "CategoryName";

    //laundry
    public static final String LAUNDRY_WEIGHT = "Weight";
    public static final String LAUNDRY_PICKUPTIME = "PickUpTime";

    //filters
    public static String Filter = "filter";
    public static String Filter_FOOD = "filter_food";
    public static String Filter_ALCOHOL = "filter_alcohol";
    public static String Filter_BEER = "filter_beer";
    public static String Filter_LIQUOR_WINE = "filter_liquor";
    public static String Filter_OTHER = "filter_other";

    //filters Keys
    public static String SortBy = "SortBy";
    public static String Rating = "Rating";
    public static String MinDeliveryTime = "MinDeliveryTime";
    public static String PriceRanges = "PriceRanges";
    public static String MinDeliveryCharges = "MinDeliveryCharges";
    public static String IsSpecial = "IsSpecial";
    public static String IsFreeDelivery = "IsFreeDelivery";
    public static String IsNewRestaurants = "IsNewRestaurants";
    public static String Cuisines = "Cuisines";
    public static String Country = "Country";
    public static String Price = "Price";
    public static String Size = "ProductNetWeight";

    //setGmailSignIn
    public static String ImageUrl = "ImageUrl";

    //setCreditCardId
    public static String Creditcard_Id = "Creditcard_Id";
    public static String Mark = "Mark";

    //setPushNotification
    public static String DeviceName = "DeviceName";
    public static String UDID = "UDID";
    public static String IsAndroidPlatform = "IsAndroidPlatform";
    public static String IsPlayStore = "IsPlayStore";
    public static String IsProduction = "IsProduction";
    public static String AuthToken = "AuthToken";

    //setStripe
    public static String StripeAccessToken = "StripeAccessToken";
    public static String StripeEmail = "StripeEmail";

    //setAlcoholFilter
    public static String Type_Id = "Type_Id";
    public static String ParentName = "ParentName";

    //setOrderDetails
    public static String OrderId = "OrderId";
    public static String StoreOrder_Id = "StoreOrder_Id";
    public static String PageSize = "PageSize";
    public static String StoreDeliverytype = "StoreDeliverytype";
}
