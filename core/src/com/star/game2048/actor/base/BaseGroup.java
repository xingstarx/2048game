package com.star.game2048.actor.base;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.star.game2048.MainGame;

/**
 * 演员组基类
 *
 * @author xietansheng
 */
public abstract class BaseGroup extends Group {

    private MainGame mainGame;

    public BaseGroup(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public MainGame getMainGame() {
        return mainGame;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }
}















