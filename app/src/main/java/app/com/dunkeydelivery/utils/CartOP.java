package app.com.dunkeydelivery.utils;

import java.util.ArrayList;

import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.modules.cart.fragments.items.CartStore;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;

/**
 * Created by Developer on 12/29/2017.
 */
public class CartOP {


    public static ArrayList<CartStore> getCarts() {
        ArrayList<CartStore> cartStores = ObjectSharedPreference.getCart();
        if (cartStores != null && cartStores.size() > 0) {
            return cartStores;
        }
        return new ArrayList<CartStore>();
    }

    //main add function to add product
    public static void addITem(ProductBO itemBO) {

        ArrayList<CartStore> cartStores = getCarts();

        if (cartStores == null || cartStores.size() == 0) {
            cartStores = new ArrayList<>();
        }

        int storePosition = getStorePosition(itemBO.getStoreId(), cartStores);
        if (storePosition != -1) {
            updateStoreinformation(storePosition, itemBO.getStoreId(), itemBO.getStoreName(), itemBO.getBusinessType(), itemBO.getMinDeliveryCharges(), itemBO.getMinDeliveryTime(), itemBO.getMinOrderPrice(), cartStores);
            int productPosition = getProductPosition(itemBO, cartStores);
            if (productPosition != -1) {
                updateProductQuantity(itemBO.quantity, storePosition, productPosition, cartStores);
            } else {
                addNewProductInStore(itemBO, storePosition, cartStores);
            }
        } else {
            addNewStoreInCart(itemBO.getStoreId(), itemBO.getStoreName(), itemBO.getBusinessType(), itemBO.getMinDeliveryCharges(), itemBO.getMinDeliveryTime(), itemBO.getMinOrderPrice(), cartStores);
            int storePositon = cartStores.size() - 1;
            addNewProductInStore(itemBO, storePositon, cartStores);
        }
        saveCart(cartStores);
    }

    //main add function to add product
    public static void addITem(ProductBO itemBO, Integer scheduleTypeId, String orderDateTime, String storeOpenFrom, String storeOpenTo, ArrayList<DeliveryTypes> deliveryTypes) {

        ArrayList<CartStore> cartStores = getCarts();

        if (cartStores == null || cartStores.size() == 0) {
            cartStores = new ArrayList<>();
        }

        int storePosition = getStorePosition(itemBO.getStoreId(), cartStores);
        if (storePosition != -1) {
            updateStoreinformation(storePosition, itemBO.getStoreId(), itemBO.getStoreName(), itemBO.getBusinessType(), itemBO.getMinDeliveryCharges(), itemBO.getMinDeliveryTime(), itemBO.getMinOrderPrice(), cartStores);
            int productPosition = getProductPosition(itemBO, cartStores);
            if (productPosition != -1) {
                updateProductQuantity(itemBO.quantity, storePosition, productPosition, cartStores);
            } else {
                addNewProductInStore(itemBO, storePosition, cartStores);
            }
        } else {
            addNewStoreInCartWithSchedule(itemBO.getStoreId(), itemBO.getStoreName(), itemBO.getBusinessType(), itemBO.getMinDeliveryCharges(), itemBO.getMinDeliveryTime(), itemBO.getMinOrderPrice(), cartStores,scheduleTypeId,orderDateTime,storeOpenFrom,storeOpenTo,deliveryTypes);
            int storePositon = cartStores.size() - 1;
            addNewProductInStore(itemBO, storePositon, cartStores);
        }
        saveCart(cartStores);
    }

    private static void updateStoreinformation(int storePosition, int id, String storeName, String businessType, Integer minDeliveryCharges, String minDeliveryTime, String minOrderPrice, ArrayList<CartStore> cartStores) {
        if (minDeliveryCharges != -1 && !minDeliveryTime.equals("-1") && !minOrderPrice.equals("-1")) {
            ArrayList<ProductBO> productBOS = cartStores.get(storePosition).getProducts();
            CartStore store = new CartStore(id, storeName, businessType, minDeliveryCharges, minDeliveryTime, minOrderPrice);
            store.setProducts(productBOS);
            cartStores.set(storePosition, store);
        }

    }

    /*private method to be accessed from add function only*/

    private static void saveCart(ArrayList<CartStore> cartStores) {
        ObjectSharedPreference.saveCart(cartStores);
    }

    //add store in existing list. called from addItem method
    private static void addNewStoreInCart(int id, String storeName, String businessType, Integer minDeliveryCharges, String minDeliveryTime, String minOrderPrice, ArrayList<CartStore> cartStores) {
        CartStore store = new CartStore(id, storeName, businessType, minDeliveryCharges, minDeliveryTime, minOrderPrice);
        cartStores.add(store);
    }

    //add store in existing list. called from addItem method
    private static void addNewStoreInCartWithSchedule(int id, String storeName, String businessType, Integer minDeliveryCharges, String minDeliveryTime, String minOrderPrice, ArrayList<CartStore> cartStores,Integer scheduleTypeId,String orderDateTime,String storeOpenFrom,String storeOpenTo,ArrayList<DeliveryTypes> deliveryTypes) {
        CartStore store = new CartStore(id, storeName, businessType, minDeliveryCharges, minDeliveryTime, minOrderPrice,scheduleTypeId,orderDateTime,storeOpenFrom,storeOpenTo,deliveryTypes);
        cartStores.add(store);
    }

    //add Proudct in existing list, in given storePosition. called from addItem method
    private static void addNewProductInStore(ProductBO productBO, int storePositon, ArrayList<CartStore> cartStores) {
        cartStores.get(storePositon).getProducts().add(productBO);
    }

    //update product quantitiy, in given storePosition and productPosition in given storePosition. called from addItem method
    private static void updateProductQuantity(int qantitiy, int storePosition, int productPosition, ArrayList<CartStore> cartStores) {
        cartStores.get(storePosition).getProducts().get(productPosition).quantity += qantitiy;
    }

    //    getting storeposition withrespect to storeId
//    if -1 is returmed then it means store was not found
    private static int getStorePosition(int storeId, ArrayList<CartStore> cartStores) {

        for (int i = 0; i < cartStores.size(); i++) {
            if (cartStores.get(i).getId() == storeId) {
                return i;
            }
        } //outer loop
        return -1;
    }

    //    getting product withrespect to proudctBO object
//    if -1 is returmed then it means product was not found
    private static int getProductPosition(ProductBO productBO, ArrayList<CartStore> cartStores) {
        for (CartStore store : cartStores) {
            ArrayList<ProductBO> storeProducts = store.getProducts();
            if (storeProducts != null && storeProducts.size() > 0)
                for (ProductBO cartProduct : storeProducts) {
                    if (productBO.getId() == cartProduct.getId()) {
                        return storeProducts.indexOf(cartProduct);
                    }
                } // inner loop
        } //outer llop

        return -1;
    }

    //    getting product withrespect to proudct id
//    if -1 is returmed then it means product was not found
    private static int getProductPosition(int productId, ArrayList<CartStore> cartStores) {
        for (CartStore store : cartStores) {
            ArrayList<ProductBO> storeProducts = store.getProducts();
            if (storeProducts != null && storeProducts.size() > 0)
                for (ProductBO cartProduct : storeProducts) {
                    if (productId == cartProduct.getId()) {
                        return storeProducts.indexOf(cartProduct);
                    }
                } //inner loop
        } //outer loop

        return -1;
    }

    /*public methods to be accessed from outside of the class*/

    //    getting storeposition withrespect to storeId
//    if -1 is returmed then it means store was not found
    public static int getStorePosition(int storeId) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (int i = 0; i < cartStores.size(); i++) {
                if (cartStores.get(i).getId() == storeId) {
                    return i;
                }
            } //inner loop
        }
        return -1;
    }

    //    getting storeposition withrespect to cartStore object
//    if -1 is returmed then it means store was not found
    public static int getStorePosition(CartStore store) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (int i = 0; i < cartStores.size(); i++) {
                if (cartStores.get(i).getId() == store.getId()) {
                    return i;
                }
            } // inner loop
        }
        return -1;
    }

    //    getting product withrespect to proudctBo object
//    if -1 is returmed then it means product was not found
    public static int getProductPosition(ProductBO productBO) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (CartStore store : cartStores) {
                ArrayList<ProductBO> storeProducts = store.getProducts();
                if (storeProducts != null && storeProducts.size() > 0) {
                    for (ProductBO cartProduct : storeProducts) {
                        if (productBO.getId() == cartProduct.getId()) {
                            return storeProducts.indexOf(cartProduct);
                        }
                    } // inner loop
                }
            } // outer loop
        }

        return -1;
    }

    //    getting product withrespect to proudct id
//    if -1 is returmed then it means product was not found
    public static int getProductPosition(int productId) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (CartStore store : cartStores) {
                ArrayList<ProductBO> storeProducts = store.getProducts();
                if (storeProducts != null && storeProducts.size() > 0) {
                    for (ProductBO cartProduct : storeProducts) {
                        if (productId == cartProduct.getId()) {
                            return storeProducts.indexOf(cartProduct);
                        }
                    } //inner loop
                }
            } // outer loop
        }
        return -1;
    }

    //    delete product from list using productBo objhect
    public static void deleteProduct(ProductBO product) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (CartStore store : cartStores) {
                if (store != null) {
                    ArrayList<ProductBO> storeProducts = store.getProducts();
                    if (storeProducts != null && storeProducts.size() > 0) {
                        for (ProductBO productBO : storeProducts) {
                            if (product.getId() == productBO.getId()) {
                                storeProducts.remove(productBO);
                                break;
                            }
                        } // inner loop

                        // if all product is removed from a store then remove the store from cart as well
                        if (storeProducts.size() == 0) {
                            cartStores.remove(store);
                            break;
                        }
                    }
                }
            } // outer loop
            saveCart(cartStores);

        }

    }

    //    delete product from list using productid
    public static void deleteProduct(int productId) {
        ArrayList<CartStore> cartStores = getCarts();
        if (cartStores != null && cartStores.size() > 0) {
            for (CartStore store : cartStores) {
                if (store != null) {
                    ArrayList<ProductBO> storeProducts = store.getProducts();
                    if (storeProducts != null && storeProducts.size() > 0) {
                        for (ProductBO productBO : storeProducts) {
                            if (productId == productBO.getId()) {
                                storeProducts.remove(productBO);
                                break;
                            }
                        } //inner loop

                        // if all product is removed from a store then remove the store from cart as well
                        if (storeProducts.size() == 0) {
                            cartStores.remove(store);
                            break;
                        }
                    }
                }
            } /// outer loop
            saveCart(cartStores);

        }
    }

    //    updae product quantitiy
//    store id and product id can be sent in this method. also
//    store position and product position can be sent in this method.
//    if storeid and product is send then you must set isidSent to true other wise it will give you exception and
//    if storeposition and productposition is send then you must set isidSent to false other wise it will give you exception
    public static void updateProductQuantity(int qantitiy, int store, int product, boolean isIdSent) {
        try {

            ArrayList<CartStore> cartStores = getCarts();

            if (cartStores != null && cartStores.size() > 0) {
                if (isIdSent) {// if it is true
                    // store position will be found from using store id
                    store = getStorePosition(store, cartStores);
                    // product position will be found from using store product
                    product = getProductPosition(product, cartStores);

                }
                cartStores.get(store).getProducts().get(product).setQuantity(cartStores.get(store).getProducts().get(product).getQuantity() + qantitiy);
                saveCart(cartStores);
            }
        } catch (Exception ex) {
            LogUtils.i("mess", "" + ex.toString());
        }

    }

    public static String getRawCart() {
        return ObjectSharedPreference.getRawCart();
    }

    public static void clearCart() {
        ObjectSharedPreference.clearCart();
    }

//    /*private boolean isStoreExists(String storeId) {
//        for (int i = 0; i < cartStores.size(); i++) {
//            if (cartStores.get(i).getId().equals(storeId)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private int getStorePosition(String id) {
//        for (int i = 0; i < cartStores.size(); i++) {
//            if (cartStores.get(i).getId().equals(id)) {
//                return i;
//            }
//        }
//        return -1;
//    }*/
//
//    /*private boolean getProductPosition(ProductBO productBO) {
//        for (int i = 0; i < cartStores.size(); i++) {
//            CartStore store = cartStores.get(i);
//            ArrayList<ProductBO> storeProducts = store.getProducts();
//            if (storeProducts == null) {
//                storeProducts = new ArrayList<>();
//                store.setProducts(storeProducts);
//            }
//            for (int j = 0; j < storeProducts.size(); j++) {
//                ProductBO cartProduct = store.getProducts().get(j);
//                if (productBO.getName().equals(cartProduct.getName())) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }*/
//
//   /* private int getProductPositon(ProductBO productBO) {
//        for (int i = 0; i < cartStores.size(); i++) {
//            CartStore store = cartStores.get(i);
//            for (int j = 0; j < store.getProducts().size(); j++) {
//                ProductBO cartProduct = store.getProducts().get(j);
//                if (productBO.getName().equals(cartProduct.getName())) {
//                    return j;
//                }
//            }
//        }
//
//        return -1;
//    }*/
}
