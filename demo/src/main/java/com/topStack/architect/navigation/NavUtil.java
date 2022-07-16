package com.topStack.architect.navigation;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.topStack.architect.R;
import com.topStack.architect.navigation.model.BottomBar;
import com.topStack.architect.navigation.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 */
public class NavUtil {
    private static HashMap<String, Destination> destinations;

    /**
     * 读取json文件
     *
     * @param context
     * @param filename
     * @return
     */
    public static String parseFile(Context context, String filename) {
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder builder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            inputStream.close();
            bufferedReader.close();

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 构建导航
     *
     * @param activity
     * @param childFm
     * @param controller
     * @param containerId
     */
    public static void builderNavGraph(FragmentActivity activity, FragmentManager childFm, NavController controller, int containerId) {
        String content = parseFile(activity, "destination.json");
        destinations = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
        });

        assert destinations != null;
        Iterator<Destination> iterator = destinations.values().iterator();
        NavigatorProvider provider = controller.getNavigatorProvider();

        NavGraphNavigator graphNavigator = provider.getNavigator(NavGraphNavigator.class);
        NavGraph navGraph = new NavGraph(graphNavigator);

        HiFragmentNavigator hiFragmentNavigator = new HiFragmentNavigator(activity, childFm, containerId);
        provider.addNavigator(hiFragmentNavigator);
        while (iterator.hasNext()) {
            Destination destination = iterator.next();
            if ("activity".equals(destination.destType)) {
                ActivityNavigator navigator = provider.getNavigator(ActivityNavigator.class);
                ActivityNavigator.Destination node = navigator.createDestination();
                node.setId(destination.id);
                node.setComponentName(new ComponentName(activity.getPackageName(), destination.clazName));
                navGraph.addDestination(node);
            } else if ("fragment".equals(destination.destType)) {
                /**
                 * 使用系统自带FragmentNavigator时存在fragment替换问题，hiFragmentNavigator将对此重写以解决此问题
                 * FragmentNavigator navigator = provider.getNavigator(FragmentNavigator.class);
                 * FragmentNavigator.Destination node = navigator.createDestination();
                 */
                HiFragmentNavigator.Destination node = hiFragmentNavigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazName);

                navGraph.addDestination(node);

            } else if ("dialog".equals(destination.destType)) {
                DialogFragmentNavigator navigator = provider.getNavigator(DialogFragmentNavigator.class);
                DialogFragmentNavigator.Destination node = navigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazName);

                navGraph.addDestination(node);
            }

            if (destination.asStarter) {
                navGraph.setStartDestination(destination.id);
            }
        }

        controller.setGraph(navGraph);
    }


    /**
     * 解析底部导航栏配置文件:main_tabs_config.json
     *
     * @param navView
     */
    public static void builderBottomBar(BottomNavigationView navView) {
        String content = parseFile(navView.getContext(), "main_tabs_config.json");
        BottomBar bottomBar = JSON.parseObject(content, BottomBar.class);

        assert bottomBar != null;
        List<BottomBar.Tab> tabs = bottomBar.tabs;
        Menu menu = navView.getMenu();
        for (BottomBar.Tab tab : tabs) {
            if (!tab.enable) {
                continue;
            }
            Destination destination = destinations.get(tab.pageUrl);
            if (destination != null) {
                MenuItem menuItem = menu.add(0, destination.id, tab.index, tab.title);
                menuItem.setIcon(R.drawable.ic_home_black_24dp);
            }
        }
    }
}
