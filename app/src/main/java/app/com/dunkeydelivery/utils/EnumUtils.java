package app.com.dunkeydelivery.utils;

import android.content.Context;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.account.fragments.ChangePassword;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.ChangeStore;
import app.com.dunkeydelivery.modules.home.tabs.food.StoreCategories;


/**
 * Created by Developer on 8/10/2015.
 */
public class EnumUtils {
    public static enum Authentications {
        TWITTER(0), FACEBOOK(1), GMAIL(2);

        private int numVal;

        Authentications(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        public static Authentications getAuthentication(int type) {

            if (type == 0) {
                return TWITTER;
            } else if (type == 1) {
                return FACEBOOK;
            } else if (type == 2) {
                return GMAIL;
            }
            return TWITTER;
        }
    }

    public static enum AppDomain {
        LIVE, QA, DEV
    }


    public static enum HomeTabs {
        Food(0), Alcohol(1), Grocery(2), Laundry(3), Pharmacy(4), Retail(5), Ride(6);

        private int numVal;

        HomeTabs(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }


        public static HomeTabs getTab(int type) {
            if (type == 0) {
                return Food;
            } else if (type == 1) {
                return Alcohol;
            } else if (type == 2) {
                return Grocery;
            } else if (type == 3) {
                return Laundry;
            } else if (type == 4) {
                return Pharmacy;
            } else if (type == 5) {
                return Retail;
            } else if (type == 6) {
                return Ride;
            }
            return Food;
        }

        public static int getTabColor(int position) {
            switch (position) {
                case 0:
                    return R.color.food_selected_color;
                case 1:
                    return R.color.alcohol_selected_color;
                case 2:
                    return R.color.grocery_selected_color;
                case 3:
                    return R.color.laundry_selected_color;
                case 4:
                    return R.color.pharmacy_selected_color;
                case 5:
                    return R.color.retail_selected_color;
                case 6:
                    return R.color.ride_selected_color;
                default:
                    return R.color.food_selected_color;
            }
        }


        public static int getName(int currentItem) {
            switch (currentItem) {
                case 0:
                    return R.string.food;
                case 1:
                    return R.string.alcohol;
                case 2:
                    return R.string.grocery;
                case 3:
                    return R.string.laundry;
                case 4:
                    return R.string.pharmacy;
                case 5:
                    return R.string.retail;
                case 6:
                    return R.string.ride;
                default:
                    return R.string.food;
            }
        }
    }

    public static enum FilterType {
        Alcohol(0), Wine(1), Liqour(2), Beer(3);

        private int numVal;

        FilterType(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        public static String getLabel(int position, Context context) {
            switch (position) {
                case 0:
                    return context.getString(R.string.filter_for_alcohol);
                case 1:
                    return context.getString(R.string.wine_filter);
                case 2:
                    return context.getString(R.string.liquor_filter);
                case 3:
                    return context.getString(R.string.beer_filter);
                default:
                    return context.getString(R.string.filter_for_alcohol);
            }
        }

        public static FilterType getType(int type) {

            if (type == 0) {
                return Alcohol;
            } else if (type == 1) {
                return Wine;
            } else if (type == 2) {
                return Liqour;
            } else if (type == 3) {
                return Beer;
            }
            return Alcohol;
        }


    }

    public static enum SortBy {
        Distance(0),
        Rating(1),
        DeliveryTime(2),
        Price(3),
        MinDelivery(4),
        AtoZ(5),
        Relevance(6);

        private int numVal;

        SortBy(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        public static SortBy getSortBy(int type) {
            if (type == 0) {
                return Distance;
            } else if (type == 1) {
                return Rating;
            } else if (type == 2) {
                return DeliveryTime;
            } else if (type == 3) {
                return Price;
            } else if (type == 4) {
                return MinDelivery;
            } else if (type == 5) {
                return AtoZ;
            } else if (type == 6) {
                return Relevance;
            } else {
                return Distance;
            }
        }

        public static int getNumValue(SortBy sortBy) {
            return sortBy.numVal;
        }
    }


    public static enum Gender {
        Male(1), Female(2), None(0);

        private int numVal;

        Gender(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        public static String getLabel(int position, Context context) {
            switch (position) {
                case 1:
                    return context.getString(R.string.male);
                case 2:
                    return context.getString(R.string.female);
                default:
                    return context.getString(R.string.male);
            }
        }

        public static Gender getGender(int type) {

            if (type == 0) {
                return Male;
            } else if (type == 1) {
                return Female;
            }
            return None;
        }
    }


    public static enum ReasonType {
        Comment(0), Question(1), Report(2), Inquiry(3), None(4);

        private int numVal;

        ReasonType(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        public static String getLabel(int position, Context context) {
            switch (position) {
                case 0:
                    return context.getString(R.string.comment_on_site);
                case 1:
                    return context.getString(R.string.question_about_site);
                case 2:
                    return context.getString(R.string.report_techincal_issue);
                case 3:
                    return context.getString(R.string.bussiness_inquiry);
                case 4:
                    return context.getString(R.string.reason_for_contact);
                default:
                    return context.getString(R.string.reason_for_contact);
            }
        }

        public static ReasonType getReason(int type) {

            if (type == 0) {
                return Comment;
            } else if (type == 1) {
                return Question;
            } else if (type == 2) {
                return Report;
            } else if (type == 3) {
                return Inquiry;
            } else if (type == 4) {
                return None;
            }
            return None;
        }

        public static int getSeasonIndex(String strTomatch) {

            if (strTomatch.equals("Autumn")) {
                return 0;
            } else if (strTomatch.equals("Summer")) {
                return 1;
            } else if (strTomatch.equals("Winter")) {
                return 2;
            } else if (strTomatch.equals("Spring")) {
                return 3;
            } else if (strTomatch.equals("Other")) {
                return 4;
            }

            return 4;
        }
    }


    public static enum ServiceName {
        GetOrdersHistoryByIdMobile,
        RegisterPushNotification,
        UpdateCreditCardById,
        RegisterWithGmail,
        ExternalService,
        login,
        Register,
        RegisterWithImage,
        PasswordResetThroughEmail,
        ChangePassword,
        RemoveCreditCard,
        ExternalLogin,
        GetCreditCards,
        AddCreditCard,
        EditUserCreditCards,
        AddAddress,
        GetStoreSchedule,
        RemoveAddress,
        EditUserAddress,
        GetUserAddresses,
        GetStoresByCategories,
        StoreCategories,
        FilterStore,
        GetMedicationNames,
        GetCategoryProducts,
        ProductByName,
        RegisterRide,
        GetStoreByIdMobile,
        SubmitContactUs,
        GetCartMobile,
        InsertOrderMobile,
        GetUserAddressesById,
        streams,
        GetRewardPrizes,
        AlcoholHomeScreen,
        AlcoholFilterStore,
        AlcoholStoreCategoryDetails,
        AlcoholFilterTypeStoreCategoryDetails,
        GetOrdersHistoryMobile,
        SubmitPharmacyRequestMobile,
        RedeemPrize,
        GetParentCategories,
        GetCategoryItems,
        ChangeStore,
        RequestGetClothMobile,
        GetCousines,
        GetSettings,
        GetOfferPackage;

        public static String getServicePath(ServiceName serviceName) {
            switch (serviceName) {
                case RegisterPushNotification:
                    return "Notification/RegisterPushNotification";
                case GetOrdersHistoryByIdMobile:
                    return "Order/GetOrdersHistoryByIdMobile";
                case UpdateCreditCardById:
                    return "User/UpdateCreditCardById";
                case RegisterWithGmail:
                    return "User/RegisterWithGmail";
                case Register:
                    return "User/Register";
                case RegisterWithImage:
                    return "User/RegisterWithImage";
                case login:
                    return "User/login";
                case PasswordResetThroughEmail:
                    return "User/PasswordResetThroughEmail";
                case ChangePassword:
                    return "User/ChangePasswordMobile";
                case ExternalLogin:
                    return "User/ExternalLogin";
                case GetCreditCards:
                    return "User/GetUserCreditCards";
                case AddCreditCard:
                    return "User/AddCreditCard";
                case RemoveCreditCard:
                    return "User/RemoveCreditCard";
                case EditUserCreditCards:
                    return "User/EditUserCreditCards";
                case AddAddress:
                    return "User/AddAddress";
                case RemoveAddress:
                    return "User/RemoveAddress";
                case EditUserAddress:
                    return "User/EditUserAddress";
                case GetUserAddresses:
                    return "User/GetUserAddresses";
                case GetStoresByCategories:
                    return "Shop/GetStoresByCategories";
                case StoreCategories:
                    return "Shop/StoreCategories";
                case FilterStore:
                    return "Shop/FilterStore";
                case GetMedicationNames:
                    return "Products/GetMedicationNames";
                case GetCategoryProducts:
                    return "Products/GetCategoryProducts";
                case ProductByName:
                    return "Products/ProductByName";
                case RegisterRide:
                    return "Ride/Register";
                case GetStoreByIdMobile:
                    return "Shop/GetStoreByIdMobile";
                case SubmitContactUs:
                    return "User/SubmitContactUs";
                case GetCartMobile:
                    return "Order/GetCartMobile";
//                    return "Order/GetCartMobileDemo"; //calling demo to test what is sent from api
                case InsertOrderMobile:
                    return "Order/InsertOrderMobile";
                case GetOrdersHistoryMobile:
                    return "Order/GetOrdersHistoryMobile";
                case GetUserAddressesById:
                    return "User/UpdateUserAddressesById";
                case GetRewardPrizes:
                    return "Reward/GetRewardPrizes";
                case RedeemPrize:
                    return "Reward/RedeemPrize";
                case AlcoholHomeScreen:
                    return "Alcohol/AlcoholHomeScreen";
                case AlcoholFilterStore:
                    return "Alcohol/AlcoholFilterStore";
                case AlcoholStoreCategoryDetails:
                    return "Alcohol/AlcoholStoreCategoryDetails";
                case AlcoholFilterTypeStoreCategoryDetails:
                    return "Alcohol/AlcoholFilterTypeStoreCategoryDetails";
                case SubmitPharmacyRequestMobile:
                    return "Pharmacy/SubmitPharmacyRequestMobile";
                case ChangeStore:
                    return "Alcohol/ChangeStore";
                case GetParentCategories:
                    return "Laundry/GetParentCategories";
                case GetCategoryItems:
                    return "Laundry/GetCategoryItems";
                case RequestGetClothMobile:
                    return "Laundry/RequestGetClothMobile";
                case GetCousines:
                    return "Shop/GetCousines";
                case GetSettings:
                    return "Settings/GetSettings";
                case GetOfferPackage:
                    return "Deals/GetOfferPackage";
                case GetStoreSchedule:
                    return "Shop/GetStoreSchedule";
                default:
                    return "Other";
            }
        }

    }

    public static enum SignInType {
        Gmail, Facebook, Rider, Other;

        public static int getSignInType(SignInType type) {
            switch (type) {
                case Facebook:
                    return 5;
                case Gmail:
                    return 6;
                case Rider:
                    return 1;
                case Other:
                    return 0;
                default:
                    return 0;
            }
        }
    }

    public static enum RequestMethod {
        GET, POST
    }

    public static enum AddressType {
        HOME, WORK, CUSTOM
    }


    public static enum ServiceResponseMessage {
        InvalidResponse, NetworkError, ServerNotReachable, ConnectionTimeOut
    }

    public static enum OrderStatus {

        INIT(0, "Initiated"), DELIVERED(8, "Delivered"), IN_PROGRESS(3, "In Progress"), SHIPPED(6, "Shipped"),;

        private int statusCode;
        private String statusText;


        OrderStatus(int statusCode, String statusText) {
            this.statusCode = statusCode;
            this.statusText = statusText;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public static String getStatusText(int statusCode) {

            if (statusCode == 0) {
                return INIT.getStatusText();
            } else if (statusCode == 3) {
                return IN_PROGRESS.getStatusText();
            } else if (statusCode == 6) {
                return SHIPPED.getStatusText();
            } else if (statusCode == 8) {
                return DELIVERED.getStatusText();
            }
            return INIT.getStatusText();
        }
    }

    public enum CheckoutFrequency {
        MONTHLY, WEEKLY, ONE_TIME;

        public static int getPosition(CheckoutFrequency frequency) {
            switch (frequency) {
                case ONE_TIME:
                    return 0;
                case WEEKLY:
                    return 1;
                case MONTHLY:
                    return 2;
                default:
                    return 0;
            }
        }

        public static String getStringName(int position) {
            switch (position) {
                case 0:
                    return "One Time";
                case 1:
                    return "Weekly";
                case 2:
                    return "Monthly";
                default:
                    return "One Time";
            }
        }

        public static String getStringName(CheckoutFrequency frequency) {
            switch (frequency) {
                case ONE_TIME:
                    return "One Time";
                case WEEKLY:
                    return "Weekly";
                case MONTHLY:
                    return "Monthly";
                default:
                    return "One Time";
            }
        }

    }
}// main
