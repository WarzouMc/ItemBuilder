import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This version of ItemBuilder is not complete and need someone method.
 * So the java doc isn't available.
 * <i>Just open ItemBuilder</i>
 * @version 0.1.3
 * @author WarzouMc
 */
public class ItemBuilder {

    /**
     * Vars
     */
    private Inventory inventory;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;

    private double position = -1;

    /**
     * init ItemBuilder without argument
     */
    public ItemBuilder(){
        this(Material.AIR);
    }

    /**
     * init ItemBuilder
     * @param material
     */
    public ItemBuilder(Material material){
        this(material, 1);
    }

    /**
     * init ItemBuilder
     * @param material
     * @param amount
     */
    public ItemBuilder(Material material, int amount) {
        this(material, amount, 0);
    }

    /**
     * init ItemBuilder
     * @param material
     * @param amount
     * @param data
     */
    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (byte)data);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * init ItemBuilder from his json object
     * @param jsonObject
     */
    public ItemBuilder(JSONObject jsonObject) {
        Object object = jsonObject.get("serialized");
        Map<String, Object> map = (Map<String, Object>) object;
        if (jsonObject.containsKey("p")){
            this.position = (double) jsonObject.get("p");
        }
        this.itemStack = ItemStack.deserialize(map);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * init ItemBuiler
     * @param itemStack
     */
    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Set item
     * @param material
     * @return
     */
    public ItemBuilder setItem(Material material){
        this.itemStack.setType(material);
        return this;
    }

    /**
     * Set amount
     * @param amount
     * @return
     */
    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set data
     * @param data
     * @return
     */
    public ItemBuilder setData(int data){
        this.itemStack = new ItemStack(itemStack.getType(), itemStack.getAmount(), (byte)data);
        return this;
    }

    /**
     * Set ItemStack
     * @param itemStack
     * @return
     */
    public ItemBuilder setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
        return this;
    }

    /**
     * set this.inventory value
     * @param inventory
     * @return
     */
    public ItemBuilder inventory(Inventory inventory){
        this.inventory = inventory;
        return this;
    }

    /**
     * @param unbreakable
     * Set item in unbreakable/breakable
     * @return
     */
    public ItemBuilder setUnbreakable(boolean unbreakable){
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    /**
     * set the display name of the item
     * @param name
     * @return
     */
    public ItemBuilder setName(String name){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Add lore from String list
     * @param lores
     * @return
     */
    public ItemBuilder addLore(List<String> lores){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Add lore from String...
     * @param lores
     * @return
     */
    public ItemBuilder addLore(String... lores){
        addLore(Arrays.asList(lores));
        return this;
    }

    /**
     * add enchant to the item
     * @param enchantment
     * @param value
     * @param b
     * @return
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int value, boolean b){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, value, b);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * add enchants from map (use for json object)
     * @param enchantment
     * @return
     */
    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchantment){
        for (Map.Entry<Enchantment, Integer> entry : enchantment.entrySet()) {
            Enchantment enchant = entry.getKey();
            addEnchantment(enchant, entry.getValue(), entry.getValue() > enchant.getMaxLevel());
        }
        return this;
    }

    /**
     * add ItemFlag on your item
     * @param itemFlag
     * @return
     */
    public ItemBuilder addItemFlag(ItemFlag itemFlag){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * remove ItemFlag on your item
     * @param itemFlag
     * @return
     */
    public ItemBuilder removeItemFlag(ItemFlag itemFlag){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * hide enchant
     * @return
     */
    public ItemBuilder hideEnchant(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * show enchant
     * @return
     */
    public ItemBuilder showEnchant(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Set durrability of item
     * /!\ 100 >= percent >= 0
     * @param percent
     * @return
     */
    public ItemBuilder setDurability(float percent){
        if (percent > 100.0){
            return this;
        }else if (percent < 0.0){
            return this;
        }
        itemStack.setDurability((short) (itemStack.getDurability() * (percent / 100)));
        return this;
    }

    /**
     * Set durrability of item
     * @param durability
     * @return
     */
    public ItemBuilder setNewDurability(int durability){
        itemStack.setDurability((short)durability);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     * @param playerName
     * @return
     */
    public ItemBuilder setSkullTextureFromePlayerName(String playerName){
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        this.skullMeta.setOwner(playerName);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     * @param player
     * @return
     */
    public ItemBuilder setSkullTexture(Player player){
        setSkullTextureFromePlayerName(player.getName());
        return this;
    }

    /**
     * If your item is a player skull you can apply a texture
     * value is the base64 value of the skull texture
     * You can find the value on https://minecraft-heads.com
     * @param value
     * @return
     */
    public ItemBuilder setSkullTexture(String value){
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value));

        try{
            Field gameProfileField = skullMeta.getClass().getDeclaredField("profile");
            gameProfileField.setAccessible(true);
            gameProfileField.set(skullMeta, gameProfile);
        } catch (IllegalAccessException | NoSuchFieldException error) {
            error.printStackTrace();
        }

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Inject item in inventory
     * @param inventory
     * @param position
     * @return
     */
    public ItemBuilder inject(Inventory inventory, int position){
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @param inventory
     * @return
     */
    public ItemBuilder inject(Inventory inventory){
        inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @param position
     * @return
     */
    public ItemBuilder inject(int position){
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @return
     */
    public ItemBuilder inject(){
        this.inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Open inventory to the player
     * @param player
     */
    public void open(Player player){
        player.openInventory(inventory);
    }

    /**
     * get position
     * @return
     */
    public long getPosition(){
        return (long) this.position;
    }

    /**
     * build item
     * @return
     */
    public ItemStack toItemStack(){
        return itemStack;
    }

    /**
     * @param itemBuilder
     * returns if two item builder are similar
     * This method compare type, data, and display name of items
     * @return
     */
    public boolean isSimilar(ItemBuilder itemBuilder){
        return hasSameMaterial(itemBuilder) && hasSameData(itemBuilder) && hasSameDisplayName(itemBuilder);
    }

    /**
     * @param itemBuilder
     * returns if two item builder are exactly same
     * This method compare all parameter of items
     * @return
     */
    public boolean isExactlySame(ItemBuilder itemBuilder){
        return hasSameMaterial(itemBuilder) && hasSameData(itemBuilder) && hasSameDisplayName(itemBuilder)
                && hasSameAmount(itemBuilder) && hasSameDurability(itemBuilder) && hasSameEnchantment(itemBuilder)
                && hasSameItemFlag(itemBuilder) && hasSameLore(itemBuilder) && hasSameBreakableStat(itemBuilder);
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same type
     * @return
     */
    public boolean hasSameMaterial(ItemBuilder itemBuilder){
        return getMaterial() == itemBuilder.getMaterial();
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same display name
     * @return
     */
    public boolean hasSameDisplayName(ItemBuilder itemBuilder){
        return getDisplayName().equalsIgnoreCase(itemBuilder.getDisplayName());
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same enchantments
     * @return
     */
    public boolean hasSameEnchantment(ItemBuilder itemBuilder){
        return getEnchantments().equals(itemBuilder.getEnchantments());
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same item flags
     * @return
     */
    public boolean hasSameItemFlag(ItemBuilder itemBuilder){
        return getItemFlag().equals(itemBuilder.getItemFlag());
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same lore
     * @return
     */
    public boolean hasSameLore(ItemBuilder itemBuilder){
        return getLore().equals(itemBuilder.getLore());
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same data
     * @return
     */
    public boolean hasSameData(ItemBuilder itemBuilder){
        return getData() == itemBuilder.getData();
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same amount
     * @return
     */
    public boolean hasSameAmount(ItemBuilder itemBuilder){
        return getAmount() == itemBuilder.getAmount();
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same durability
     * @return
     */
    public boolean hasSameDurability(ItemBuilder itemBuilder){
        return getDurability() == itemBuilder.getDurability();
    }

    /**
     * @param itemBuilder
     * returns if two item builder has same breakable stat
     * @return
     */
    public boolean hasSameBreakableStat(ItemBuilder itemBuilder){
        return isUnbreakable() == itemBuilder.isUnbreakable();
    }

    /**
     * get type
     * @return
     */
    public Material getMaterial(){
        return itemStack.getType();
    }

    /**
     * get amount
     * @return
     */
    public int getAmount(){
        return itemStack.getAmount();
    }

    /**
     * get data
     * @return
     */
    public int getData(){
        return itemStack.getData().getData();
    }

    /**
     * get durability
     * @return
     */
    public int getDurability(){
        return itemStack.getDurability();
    }

    /**
     * get item meta
     * @return
     */
    public ItemMeta getItemMeta(){
        return itemMeta;
    }

    /**
     * get display name
     * @return
     */
    public String getDisplayName(){
        return itemStack.hasItemMeta() && itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null;
    }

    /**
     * get enchant
     * @return
     */
    public Map<Enchantment, Integer> getEnchantments(){
        return itemStack.hasItemMeta() && itemMeta.hasEnchants() ? itemMeta.getEnchants() : null;
    }

    /**
     * get lore
     * @return
     */
    public List<String> getLore(){
        return itemStack.hasItemMeta() && itemMeta.hasLore() ? itemMeta.getLore() : Collections.singletonList("");
    }

    /**
     * get item flag
     * @return
     */
    public Set<ItemFlag> getItemFlag(){
        return itemStack.hasItemMeta() && itemMeta.getItemFlags().size() > 0 ? itemMeta.getItemFlags() : null;
    }

    /**
     * get if item is or isn't unbreakable
     * @return
     */
    public boolean isUnbreakable(){
        return itemStack.hasItemMeta() && itemMeta.spigot().isUnbreakable();
    }

    /**
     * parse in json object
     * @param savePositionInInventory
     * @return
     */
    @SuppressWarnings("uncheked")
    public JSONObject toJSONObject(int savePositionInInventory){
        JSONObject jsonObject = toJSONObject();
        if (savePositionInInventory > -1) jsonObject.put("p", savePositionInInventory + 0.0);
        return jsonObject;
    }

    /**
     * parse in json object without associate position
     * @return
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> map = this.itemStack.serialize();
        jsonObject.put("serialized", map);
        return jsonObject;
    }

    /**
     * @param string
     * @return
     */
    private String s_C(String string){
        return string.replace("§", "&");
    }

    /**
     * @param string
     * @return
     */
    private String c_S(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
