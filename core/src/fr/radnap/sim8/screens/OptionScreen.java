package fr.radnap.sim8.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import fr.radnap.sim8.Options;
import fr.radnap.sim8.SIM8;

import javax.swing.text.html.Option;

/**
 * @author Radnap
 */
public class OptionScreen extends AbstractScreen {

	private Stage stage;
	private Cell optionsCell;
	private Table optionsTable;
	private Table graphicTable;

	private int selected;


	public OptionScreen(SIM8 game) {
		super(game);
	}

	@Override
	public void loadAssets() {
		if (stage != null)
			return;

		super.loadAssets();
	}

	@Override
	public void create() {
		if (stage != null)
			return;

		super.create();

		stage = new Stage(viewport, game.batch);

		TextureAtlas atlas = game.assetManager.get("mainMenu/mainMenuPack.atlas", TextureAtlas.class);

		// Create the screen background and adapt the world to it
		Image screenBg = new Image(new TiledDrawable(atlas.findRegion("metal_panel")));

		float ratio = SCREEN_WIDTH / (4f * screenBg.getWidth());
		screenBg.setY(-screenBg.getHeight() / 2f);
		screenBg.setSize(SCREEN_WIDTH, SCREEN_HEIGHT + screenBg.getHeight() / 2f);

		stage.addActor(screenBg);

		/// Create skin and fonts
		Skin skin = new Skin();

		FreeTypeFontGenerator.FreeTypeFontParameter fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParams.minFilter = Texture.TextureFilter.Linear;
		fontParams.magFilter = Texture.TextureFilter.Linear;
		fontParams.size = 80;
		fontParams.shadowColor = new Color(.1f, 0f, 0f, 0.4f);
		fontParams.shadowOffsetX = 3;
		fontParams.shadowOffsetY = 3;
		BitmapFont font = SIM8.title2Gen.generateFont(fontParams);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.overFontColor = Color.WHITE;
		textButtonStyle.fontColor = Color.GRAY;
		skin.add("default", textButtonStyle);


		final Table table = new Table(skin);
		stage.addActor(table);
		table.setFillParent(true);
		table.left().bottom();
		// Setting the default value of the cells
		table.defaults().left().spaceTop(Value.percentHeight(0.5f));
		table.setX(0.15f * SCREEN_WIDTH);
		table.setY(0.3f * SCREEN_HEIGHT);


		optionsTable = new Table(skin);
		optionsTable.defaults().left().padLeft(30f).spaceBottom(Value.percentHeight(1f));

		TextButton soundButton = new TextButton("Son : yes", skin);
		if (!Options.sound)
			soundButton.setText("Son: no");
		soundButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TextButton soundButton = (TextButton) actor;
				if (Options.sound) {
					soundButton.setText("Son : no");
					Options.sound = false;
				} else {
					soundButton.setText("Son : yes");
					Options.sound = true;
				}
			}
		});
		optionsTable.add(soundButton).row();

		TextButton tutorialButton = new TextButton("Tutorial : yes", skin);
		if (!Options.tutorial)
			tutorialButton.setText("Tutorial: no");
		tutorialButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TextButton tutorialButton = (TextButton) actor;
				if (Options.tutorial) {
					tutorialButton.setText("Tutorial : no");
					Options.tutorial = false;
				} else {
					tutorialButton.setText("Tutorial : yes");
					Options.tutorial = true;
				}
			}
		});
		optionsTable.add(tutorialButton).row();

		TextButton graphicButton = new TextButton("Resolution", skin);
		graphicButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				optionsCell.setActor(graphicTable);
			}
		});
		optionsTable.add(graphicButton).row();

		TextButton backButton = new TextButton("<Back", skin);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.mainMenuScreen);
			}
		});
		optionsTable.add(backButton).padLeft(0).row();


		graphicTable = new Table(skin);
		graphicTable.defaults().left().padLeft(30f).spaceBottom(Value.percentHeight(1f));

		TextButton averageResolution = new TextButton("1280*720", skin);
		averageResolution.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.graphics.setWindowedMode(1280, 720);
			}
		});
		graphicTable.add(averageResolution).row();

		TextButton highResolution = new TextButton("1920*1080", skin);
		highResolution.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.graphics.setWindowedMode(1920, 1080);
			}
		});
		graphicTable.add(highResolution).row();

		backButton = new TextButton("<Back", skin);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				optionsCell.setActor(optionsTable);
			}
		});
		graphicTable.add(backButton).padLeft(0).row();


		optionsCell = table.add(optionsTable);
		optionsCell.row();
		table.layout();
	}

	@Override
	public void show() {
		super.show();

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		stage.setDebugAll(SIM8.DEVMODE);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		super.dispose();

		if (stage != null) {
			stage.dispose();
			stage = null;
		}
	}
}
