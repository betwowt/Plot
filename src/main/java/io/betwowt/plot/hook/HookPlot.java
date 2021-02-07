package io.betwowt.plot.hook;

import com.plotsquared.bukkit.BukkitMain;
import com.plotsquared.core.Platform;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

public class HookPlot {

    public Set<Plot> getAllPlots(){
        return PlotSquared.get().getPlots();
    }

}
