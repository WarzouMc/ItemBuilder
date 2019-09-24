package yourPackage;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

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
