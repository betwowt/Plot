package io.betwowt.plot.elements;

import com.plotsquared.core.plot.Plot;
import io.betwowt.plot.pickers.PlotListPicker;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.SpigotTechPlugin;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;

public class PlotListSelector extends Element {

    private UltraCustomizer plugin;

    public PlotListSelector(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
        this.plugin = ultraCustomizer;
    }

    @Override
    public String getName() {
        return "Plot list Selector";
    }

    @Override
    public String getInternalName() {
        return "Plot list Selector";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.GRASS_BLOCK;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"show all plot"};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{new Argument("player", "Chooser", DataType.PLAYER, elementInfo), new Argument("title", "GUI Title", DataType.STRING, elementInfo)};
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{new OutcomingVariable("selected-Plot", "Selected Plot", DataType.STRING, elementInfo)};
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{(Child) new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) getArguments(elementInfo)[0].getValue(scriptInstance);
        String str = (String) getArguments(elementInfo)[1].getValue(scriptInstance);
        if (player != null && player.isOnline())
            new PlotListPicker(player, (SpigotTechPlugin) this.plugin, str) {
                public void onSelect(final Plot plot) {
                    PlotListSelector.this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
                        public Object request() {
                            return plot.getId().toCommaSeparatedString();
                        }
                    });
                    PlotListSelector.this.getConnectors(elementInfo)[0].run(scriptInstance);
                }
            };
    }

}

