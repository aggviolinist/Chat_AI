package com.apps.aivision.data.repository

import android.app.Application
import com.apps.aivision.R
import com.apps.aivision.components.ConversationType
import com.apps.aivision.data.model.AiPromptModel
import com.apps.aivision.data.model.AiPromptsCategoryModel
import com.apps.aivision.data.model.StyleModel
import javax.inject.Inject


interface LocalResourceRepository {

    fun getTextExamples():List<String>
    fun getPrompts(): List<AiPromptsCategoryModel>
    fun getFiltersOptions():List<String>
    fun getDefaultPrompts(): List<AiPromptsCategoryModel>
    fun getStyles(): List<StyleModel>
}

class LocalResourceRepositoryImpl @Inject constructor(private val app: Application):LocalResourceRepository {

    override fun getTextExamples(): List<String> = mutableListOf(
        app.getString( R.string.examples_1),
        app.getString( R.string.examples_2),
        app.getString(R.string.examples_3)
    )



    override fun getPrompts(): List<AiPromptsCategoryModel> = listOf(
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.social_media),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.sc_facebook,
                    title = app.getString(R.string.social_media_fb),
                    summery = app.getString(R.string.social_media_fb_description),
                    examplesList = listOf(
                        app.getString(R.string.social_media_fb_example1),
                        app.getString(R.string.social_media_fb_example2),
                        app.getString(R.string.social_media_fb_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_insta,
                    title = app.getString(R.string.social_media_insta),
                    summery = app.getString(R.string.social_media_insta_description),
                    examplesList = listOf(
                        app.getString(R.string.social_media_insta_example1),
                        app.getString(R.string.social_media_insta_example2),
                        app.getString(R.string.social_media_insta_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_tweet,
                    title = app.getString(R.string.social_media_tweet),
                    summery = app.getString(R.string.social_media_tweet_description),
                    examplesList = listOf(
                        app.getString(R.string.social_media_tweet_example1),
                        app.getString(R.string.social_media_tweet_example2),
                        app.getString(R.string.social_media_tweet_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_linkedin,
                    title = app.getString(R.string.social_media_linkdin),
                    summery = app.getString(R.string.social_media_linkdin_description),
                    examplesList = listOf(
                        app.getString(R.string.social_media_linkdin_example1),
                        app.getString(R.string.social_media_linkdin_example2),
                        app.getString(R.string.social_media_linkdin_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_tiktok,
                    title = app.getString(R.string.social_media_tiktok),
                    summery = app.getString(R.string.social_media_tiktok_description),
                    examplesList = listOf(
                        app.getString(R.string.social_media_tiktok_example1),
                        app.getString(R.string.social_media_tiktok_example2),
                        app.getString(R.string.social_media_tiktok_example3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString(R.string.business),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.bs_marketing,
                    title = app.getString(R.string.business_marketing),
                    summery = app.getString(R.string.business_marketing_description),
                    examplesList = listOf(
                        app.getString(R.string.business_marketing_example1),
                        app.getString(R.string.business_marketing_example2),
                        app.getString(R.string.business_marketing_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_sales,
                    title = app.getString(R.string.business_sales),
                    summery = app.getString(R.string.business_sales_description),
                    examplesList = listOf(
                        app.getString(R.string.business_sales_example1),
                        app.getString(R.string.business_sales_example2),
                        app.getString(R.string.business_sales_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_email_camp,
                    title = app.getString(R.string.business_email),
                    summery = app.getString(R.string.business_email_description),
                    examplesList = listOf(
                        app.getString(R.string.business_email_example1),
                        app.getString(R.string.business_email_example2),
                        app.getString(R.string.business_email_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_customer,
                    title = app.getString(R.string.business_cs),
                    summery = app.getString(R.string.business_cs_description),
                    examplesList = listOf(
                        app.getString(R.string.business_cs_example1),
                        app.getString(R.string.business_cs_example2),
                        app.getString(R.string.business_cs_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_ecom,
                    title = app.getString(R.string.business_ec),
                    summery = app.getString(R.string.business_ec_description),
                    examplesList = listOf(
                        app.getString(R.string.business_ec_example1),
                        app.getString(R.string.business_ec_example2),
                        app.getString(R.string.business_ec_example3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.writing),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.wt_article,
                    title = app.getString(R.string.write_article),
                    summery = app.getString(R.string.write_article_description),
                    examplesList = listOf(
                        app.getString(R.string.write_article_example1),
                        app.getString(R.string.write_article_example2),
                        app.getString(R.string.write_article_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_blog,
                    title = app.getString(R.string.writing_blog),
                    summery = app.getString(R.string.writing_blog_description),
                    examplesList = listOf(
                        app.getString(R.string.writing_blog_example1),
                        app.getString(R.string.writing_blog_example2),
                        app.getString(R.string.writing_blog_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_edu,
                    title = app.getString(R.string.writing_edu),
                    summery = app.getString(R.string.writing_edu_description),
                    examplesList = listOf(
                        app.getString(R.string.writing_edu_example1),
                        app.getString(R.string.writing_edu_example2),
                        app.getString(R.string.writing_edu_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_email,
                    title = app.getString(R.string.writing_email),
                    summery = app.getString(R.string.writing_email_description),
                    examplesList = listOf(
                        app.getString(R.string.writing_email_example1),
                        app.getString(R.string.writing_email_example2),
                        app.getString(R.string.writing_email_example3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.programing),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.pg_mobile,
                    title = app.getString(R.string.programing_mobile),
                    summery = app.getString(R.string.programing_mobile_description),
                    examplesList = listOf(
                        app.getString(R.string.programing_mobile_example1),
                        app.getString(R.string.programing_mobile_example2),
                        app.getString(R.string.programing_mobile_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.pg_web,
                    title = app.getString(R.string.programing_web),
                    summery = app.getString(R.string.programing_web_description),
                    examplesList = listOf(
                        app.getString(R.string.programing_web_example1),
                        app.getString(R.string.programing_web_example2),
                        app.getString(R.string.programing_web_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.pg_server,
                    title = app.getString(R.string.programing_server),
                    summery = app.getString(R.string.programing_server_description),
                    examplesList = listOf(
                        app.getString(R.string.programing_server_example1),
                        app.getString(R.string.programing_server_example2),
                        app.getString(R.string.programing_server_example3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.life_style),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.ls_health,
                    title = app.getString(R.string.life_style_health),
                    summery = app.getString(R.string.life_style_health_description),
                    examplesList = listOf(
                        app.getString(R.string.life_style_health_example1),
                        app.getString(R.string.life_style_health_example2),
                        app.getString(R.string.life_style_health_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.ls_cooking,
                    title = app.getString(R.string.life_style_cooking),
                    summery = app.getString(R.string.life_style_cooking_description),
                    examplesList = listOf(
                        app.getString(R.string.life_style_cooking_example1),
                        app.getString(R.string.life_style_cooking_example2),
                        app.getString(R.string.life_style_cooking_example3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.ls_travel,
                    title = app.getString(R.string.life_style_travel),
                    summery = app.getString(R.string.life_style_travel_description),
                    examplesList = listOf(
                        app.getString(R.string.life_style_travel_example1),
                        app.getString(R.string.life_style_travel_example2),
                        app.getString(R.string.life_style_travel_example3)
                    )
                )
            ))
    )

    override fun getFiltersOptions(): List<String> = listOf(app.getString( R.string.social_media),
        app.getString(R.string.business),app.getString( R.string.writing),app.getString( R.string.programing),app.getString( R.string.life_style))

    override fun getDefaultPrompts(): List<AiPromptsCategoryModel> = listOf(AiPromptsCategoryModel(categoryTitle = app.getString( R.string.social_media),
        prompts = listOf(
            AiPromptModel(
                image = R.drawable.sc_facebook,
                title = app.getString(R.string.social_media_fb),
                summery = app.getString(R.string.social_media_fb_description),
                examplesList = listOf(
                    app.getString(R.string.social_media_fb_example1),
                    app.getString(R.string.social_media_fb_example2),
                    app.getString(R.string.social_media_fb_example3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_insta,
                title = app.getString(R.string.social_media_insta),
                summery = app.getString(R.string.social_media_insta_description),
                examplesList = listOf(
                    app.getString(R.string.social_media_insta_example1),
                    app.getString(R.string.social_media_insta_example2),
                    app.getString(R.string.social_media_insta_example3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_tweet,
                title = app.getString(R.string.social_media_tweet),
                summery = app.getString(R.string.social_media_tweet_description),
                examplesList = listOf(
                    app.getString(R.string.social_media_tweet_example1),
                    app.getString(R.string.social_media_tweet_example2),
                    app.getString(R.string.social_media_tweet_example3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_linkedin,
                title = app.getString(R.string.social_media_linkdin),
                summery = app.getString(R.string.social_media_linkdin_description),
                examplesList = listOf(
                    app.getString(R.string.social_media_linkdin_example1),
                    app.getString(R.string.social_media_linkdin_example2),
                    app.getString(R.string.social_media_linkdin_example3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_tiktok,
                title = app.getString(R.string.social_media_tiktok),
                summery = app.getString(R.string.social_media_tiktok_description),
                examplesList = listOf(
                    app.getString(R.string.social_media_tiktok_example1),
                    app.getString(R.string.social_media_tiktok_example2),
                    app.getString(R.string.social_media_tiktok_example3)
                )
            )
        )))

    override fun getStyles(): List<StyleModel> = listOf(
        StyleModel(app.getString(R.string.style_no),"none", R.drawable.baseline_do_disturb_alt_24),
        StyleModel(app.getString(R.string.style_enhance),"enhance",R.drawable.enhance),
        StyleModel(app.getString(R.string.style_anime),"anime",R.drawable.anime),
        StyleModel(app.getString(R.string.style_photographic),"photographic",R.drawable.photographic),
        StyleModel(app.getString(R.string.style_digital_art),"digital-art",R.drawable.digital_art),
        StyleModel(app.getString(R.string.style_comic_book),"comic-book",R.drawable.comic_book),
        StyleModel(app.getString(R.string.style_fantasy_art),"fantasy-art",R.drawable.fantasy_art),
        StyleModel(app.getString(R.string.style_line_art),"line-art",R.drawable.line_art),
        StyleModel(app.getString(R.string.style_analog_film),"analog-film",R.drawable.analog_film),
        StyleModel(app.getString(R.string.style_neon_punk),"neon-punk",R.drawable.neo_punk),
        StyleModel(app.getString(R.string.style_isometric),"isometric",R.drawable.isometric),
        StyleModel(app.getString(R.string.style_low_poly),"low-poly",R.drawable.low_poly),
        StyleModel(app.getString(R.string.style_origami),"origami",R.drawable.origami),
        StyleModel(app.getString(R.string.style_modeling_compound),"modeling-compound",R.drawable.modelling_comp),
        StyleModel(app.getString(R.string.style_cinematic),"cinematic",R.drawable.cinematic),
        StyleModel(app.getString(R.string.style_3d_model),"3d-model",R.drawable.threed_model),
        StyleModel(app.getString(R.string.style_pixel_art),"pixel-art",R.drawable.pixel_art),
        StyleModel(app.getString(R.string.style_tile_texture),"tile-texture",R.drawable.tile_texture))

}