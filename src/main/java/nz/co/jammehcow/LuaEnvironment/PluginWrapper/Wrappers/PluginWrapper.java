package nz.co.jammehcow.LuaEnvironment.PluginWrapper.Wrappers;

import nz.co.jammehcow.Handlers.EventHandlers;
import nz.co.jammehcow.LuaEnvironment.PluginWrapper.Plugin;
import nz.co.jammehcow.LuaEnvironment.PluginWrapper.PluginCommand;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import static nz.co.jammehcow.Handlers.EventHandlers.events;

/**
 * @author jammehcow
 */

public class PluginWrapper extends LuaTable {
    public PluginWrapper(Plugin plugin) {
        set("getDescription", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return CoerceJavaToLua.coerce(plugin.getDescription());
            }
        });

        set("registerCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.addCmd(new PluginCommand(plugin, args.tojstring(1), args.tojstring(2), args.checkfunction(3)));
                return LuaValue.NIL;
            }
        });

        set("registerEvent", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                EventHandlers.registeredEvents.get(events.get(args.toint(1)).getSimpleName()).add(args.checkfunction(2));
                return LuaValue.NIL;
            }
        });

        set("pluginEnable", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.setEnableCB(args.checkfunction(1));
                return LuaValue.NIL;
            }
        });

        set("pluginDisable", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.setDisableCB(args.checkfunction(1));
                return LuaValue.NIL;
            }
        });
    }
}
