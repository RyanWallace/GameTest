package com.sim.test.game.Helpers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UI extends BitmapFont {

	private Batch batch;
	
	public UI(Batch batch) {
		this.batch = batch;
	}
	
	public void uiAddText(String uiTemplateName, String uiTemplateValue, float x, float y) {
		super.draw(batch, uiTemplateName + " : " + uiTemplateValue, x, y);
	}

	public void uiAddText(String uiTemplateName, int uiTemplateValue, float x, float y) {
		super.draw(batch, uiTemplateName + " : " + uiTemplateValue, x, y);
	}
	
}
