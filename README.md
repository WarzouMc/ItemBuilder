[![Discord](https://img.shields.io/discord/577196219252604942.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/5xQPmD2)
Support on discord

ItemBuilder class is a java class for Spigot API (for minecraft plugin).

This class is not finish, I add methods if I need it.

For create Item you can call the class "ItemBuilder" : new ItemBuilder.
In parametre you can put Material or Material material, int amount or Material material, int amount, int data.

Then that you can just add enchantments, lore and set name att the item (for the time being).

Now you have 2 possibilities :
- return ItemStack
- place your item directly in an inventory

For return ItemStack you can use .toItemStack().

And for add in an inventory you can use .inject()

/!\ For the inject methode with out use the methodes who is asking Inventory in parametre you must to define your inventory with .inventory(Inventory).

You can add item with position (.inject([Inventory inventory], int position)) or without (.inject([Inventory inventory])).
