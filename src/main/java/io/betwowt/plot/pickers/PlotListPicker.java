package io.betwowt.plot.pickers;

import com.plotsquared.core.api.PlotAPI;
import com.plotsquared.core.plot.Plot;
import io.betwowt.plot.hook.HookPlot;
import me.TechsCode.UltraCustomizer.ColorPalette;
import me.TechsCode.UltraCustomizer.base.SpigotTechPlugin;
import me.TechsCode.UltraCustomizer.base.gui.Button;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.BasicSearch;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.PageableGUI;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.SearchFeature;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.visual.Animation;
import me.TechsCode.UltraCustomizer.base.visual.Color;
import me.TechsCode.UltraCustomizer.base.visual.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class PlotListPicker extends PageableGUI<Plot> {

    private String title;

    private Player player;

    public PlotListPicker(Player player, SpigotTechPlugin plugin, String str) {
        super(player, plugin);
        this.title = str;
        this.player = player;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onBack() {
        this.player.closeInventory();
    }

    @Override
    public SearchFeature<Plot> getSearch() {
        return (SearchFeature<Plot>)new BasicSearch<Plot>() {
            public String[] getSearchableText(Plot plot) {
                return new String[] { Bukkit.getOfflinePlayer(plot.getOwner()).getName() };
            }
        };
    }

    @Override
    public Plot[] getObjects() {
        return (Plot[]) (new HookPlot().getAllPlots().toArray((Object[])new Plot[0]));
    }

    @Override
    public void construct(Button button, Plot plot) {

        button.material(XMaterial.GRASS_BLOCK)
                .name(Animation.wave(plot.getId().toCommaSeparatedString(),
                        ColorPalette.MAIN, Colors.WHITE))
                .lore("§2§l owner: "+Bukkit.getOfflinePlayer(plot.getOwner()).getName(),
                        "§2§l alias: "+plot.getAlias());
        button.action(paramActionType -> {
            this.p.closeInventory();
            onSelect(plot);
        });
    }

    public abstract void onSelect(Plot plot);
}
