package fr.WarzouMc.SkyExpanderInternalPlugin.utils.graphics.itemBuilder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <i>Just open ItemBuilder</i>
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
    }

    /**
     * init ItemBuiler
     * @param itemStack
     */
    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
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
     * set the display name of the item
     * @param var1
     * @return
     */
    public ItemBuilder setName(String var1){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(var1);
        itemStack.setItemMeta(itemMeta);
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
        return this;
    }

    /**
     * hide enchant
     * @return
     */
    public ItemBuilder hideEnchante(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
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
     * build item
     * @return
     */
    public ItemStack toItemStack(){
        return itemStack;
    }
}
