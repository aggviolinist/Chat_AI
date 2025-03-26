package com.apps.imageAI.data.repository

import android.app.Application
import com.apps.imageAI.R
import com.apps.imageAI.components.ConversationType
import com.apps.imageAI.data.model.AiPromptModel
import com.apps.imageAI.data.model.AiPromptsCategoryModel
import com.apps.imageAI.data.model.StyleModel
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
        app.getString( R.string.sample_1),
        app.getString( R.string.sample_2),
        app.getString(R.string.sample_3)
    )



    override fun getPrompts(): List<AiPromptsCategoryModel> = listOf(
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.social_media),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.sc_facebook,
                    title = app.getString(R.string.social_media_fb),
                    summery = app.getString(R.string.social_media_fb_desc),
                    examplesList = listOf(
                        app.getString(R.string.social_media_fb_sample1),
                        app.getString(R.string.social_media_fb_sample2),
                        app.getString(R.string.social_media_fb_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_insta,
                    title = app.getString(R.string.social_media_insta),
                    summery = app.getString(R.string.social_media_insta_desc),
                    examplesList = listOf(
                        app.getString(R.string.social_media_insta_sample1),
                        app.getString(R.string.social_media_insta_sample2),
                        app.getString(R.string.social_media_insta_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_tweet,
                    title = app.getString(R.string.social_media_x),
                    summery = app.getString(R.string.social_media_tweet_desc),
                    examplesList = listOf(
                        app.getString(R.string.social_media_tweet_sample1),
                        app.getString(R.string.social_media_tweet_sample2),
                        app.getString(R.string.social_media_tweet_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_linkedin,
                    title = app.getString(R.string.social_media_linkdin),
                    summery = app.getString(R.string.social_media_linkdin_desc),
                    examplesList = listOf(
                        app.getString(R.string.social_media_linkdin_sample1),
                        app.getString(R.string.social_media_linkdin_sample2),
                        app.getString(R.string.social_media_linkdin_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.sc_tiktok,
                    title = app.getString(R.string.socials_tiktok),
                    summery = app.getString(R.string.socials_tiktok_desc),
                    examplesList = listOf(
                        app.getString(R.string.socials_tiktok_sample1),
                        app.getString(R.string.socials_tiktok_sample2),
                        app.getString(R.string.socials_tiktok_sample3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString(R.string.biz),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.bs_marketing,
                    title = app.getString(R.string.biz_marketing),
                    summery = app.getString(R.string.biz_marketing_desc),
                    examplesList = listOf(
                        app.getString(R.string.biz_marketing_sample1),
                        app.getString(R.string.biz_marketing_sample2),
                        app.getString(R.string.biz_marketing_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_sales,
                    title = app.getString(R.string.biz_sales),
                    summery = app.getString(R.string.biz_sales_desc),
                    examplesList = listOf(
                        app.getString(R.string.biz_sales_sample1),
                        app.getString(R.string.biz_sales_sample2),
                        app.getString(R.string.biz_sales_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_email_camp,
                    title = app.getString(R.string.biz_mail),
                    summery = app.getString(R.string.biz_mail_desc),
                    examplesList = listOf(
                        app.getString(R.string.biz_mail_sample1),
                        app.getString(R.string.biz_mail_sample2),
                        app.getString(R.string.biz_mail_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_customer,
                    title = app.getString(R.string.biz_cs),
                    summery = app.getString(R.string.biz_cs_desc),
                    examplesList = listOf(
                        app.getString(R.string.biz_cs_sample1),
                        app.getString(R.string.biz_cs_sample2),
                        app.getString(R.string.biz_cs_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.bs_ecom,
                    title = app.getString(R.string.biz_ec),
                    summery = app.getString(R.string.biz_ec_desc),
                    examplesList = listOf(
                        app.getString(R.string.biz_ec_sample1),
                        app.getString(R.string.biz_ec_sample2),
                        app.getString(R.string.biz_ec_sample3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.write),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.wt_article,
                    title = app.getString(R.string.write_article),
                    summery = app.getString(R.string.write_articlez_desc),
                    examplesList = listOf(
                        app.getString(R.string.write_articlez_sample1),
                        app.getString(R.string.write_articlez_sample2),
                        app.getString(R.string.write_articlez_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_blog,
                    title = app.getString(R.string.write_blogz),
                    summery = app.getString(R.string.write_blogz_desc),
                    examplesList = listOf(
                        app.getString(R.string.write_blogz_sample1),
                        app.getString(R.string.write_blogz_sample2),
                        app.getString(R.string.write_blogz_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_edu,
                    title = app.getString(R.string.write_edule),
                    summery = app.getString(R.string.write_edule_desc),
                    examplesList = listOf(
                        app.getString(R.string.write_edule_sample1),
                        app.getString(R.string.write_edule_sample2),
                        app.getString(R.string.write_edule_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.wt_email,
                    title = app.getString(R.string.write_mailz),
                    summery = app.getString(R.string.write_mailz_desc),
                    examplesList = listOf(
                        app.getString(R.string.write_mailz_sample1),
                        app.getString(R.string.write_mailz_sample2),
                        app.getString(R.string.write_mailz_sample3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.code),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.pg_mobile,
                    title = app.getString(R.string.code_app),
                    summery = app.getString(R.string.code_app_desc),
                    examplesList = listOf(
                        app.getString(R.string.code_app_sample1),
                        app.getString(R.string.code_app_sample2),
                        app.getString(R.string.code_app_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.pg_web,
                    title = app.getString(R.string.code_web),
                    summery = app.getString(R.string.code_web_desc),
                    examplesList = listOf(
                        app.getString(R.string.code_web_sample1),
                        app.getString(R.string.code_web_sample2),
                        app.getString(R.string.code_web_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.pg_server,
                    title = app.getString(R.string.DA),
                    summery = app.getString(R.string.DA_desc),
                    examplesList = listOf(
                        app.getString(R.string.DA_sample1),
                        app.getString(R.string.DA_sample2),
                        app.getString(R.string.DA_sample3)
                    )
                )
            ))
        ,
        AiPromptsCategoryModel(categoryTitle = app.getString( R.string.vibez),
            prompts = listOf(
                AiPromptModel(
                    image = R.drawable.ls_health,
                    title = app.getString(R.string.vibez_wellness),
                    summery = app.getString(R.string.vibez_wellness_desc),
                    examplesList = listOf(
                        app.getString(R.string.vibez_wellness_sample1),
                        app.getString(R.string.vibez_wellness_sample2),
                        app.getString(R.string.vibez_wellness_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.ls_cooking,
                    title = app.getString(R.string.nutrition),
                    summery = app.getString(R.string.nutrition_desc),
                    examplesList = listOf(
                        app.getString(R.string.nutrition_sample1),
                        app.getString(R.string.nutrition_sample2),
                        app.getString(R.string.nutrition_sample3)
                    )
                ),
                AiPromptModel(
                    image = R.drawable.ls_travel,
                    title = app.getString(R.string.travel),
                    summery = app.getString(R.string.travel_desc),
                    examplesList = listOf(
                        app.getString(R.string.travel_sample1),
                        app.getString(R.string.travel_sample2),
                        app.getString(R.string.travel_sample3)
                    )
                )
            ))
    )

    override fun getFiltersOptions(): List<String> = listOf(app.getString( R.string.social_media),
        app.getString(R.string.biz),app.getString( R.string.write),app.getString( R.string.code),app.getString( R.string.vibez))

    override fun getDefaultPrompts(): List<AiPromptsCategoryModel> = listOf(AiPromptsCategoryModel(categoryTitle = app.getString( R.string.social_media),
        prompts = listOf(
            AiPromptModel(
                image = R.drawable.sc_facebook,
                title = app.getString(R.string.social_media_fb),
                summery = app.getString(R.string.social_media_fb_desc),
                examplesList = listOf(
                    app.getString(R.string.social_media_fb_sample1),
                    app.getString(R.string.social_media_fb_sample2),
                    app.getString(R.string.social_media_fb_sample3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_insta,
                title = app.getString(R.string.social_media_insta),
                summery = app.getString(R.string.social_media_insta_desc),
                examplesList = listOf(
                    app.getString(R.string.social_media_insta_sample1),
                    app.getString(R.string.social_media_insta_sample2),
                    app.getString(R.string.social_media_insta_sample3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_tweet,
                title = app.getString(R.string.social_media_x),
                summery = app.getString(R.string.social_media_tweet_desc),
                examplesList = listOf(
                    app.getString(R.string.social_media_tweet_sample1),
                    app.getString(R.string.social_media_tweet_sample2),
                    app.getString(R.string.social_media_tweet_sample3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_linkedin,
                title = app.getString(R.string.social_media_linkdin),
                summery = app.getString(R.string.social_media_linkdin_desc),
                examplesList = listOf(
                    app.getString(R.string.social_media_linkdin_sample1),
                    app.getString(R.string.social_media_linkdin_sample2),
                    app.getString(R.string.social_media_linkdin_sample3)
                )
            ),
            AiPromptModel(
                image = R.drawable.sc_tiktok,
                title = app.getString(R.string.socials_tiktok),
                summery = app.getString(R.string.socials_tiktok_desc),
                examplesList = listOf(
                    app.getString(R.string.socials_tiktok_sample1),
                    app.getString(R.string.socials_tiktok_sample2),
                    app.getString(R.string.socials_tiktok_sample3)
                )
            )
        )))

    override fun getStyles(): List<StyleModel> = listOf(
        StyleModel(app.getString(R.string.stylez_no),"none", R.drawable.baseline_do_disturb_alt_24),
        StyleModel(app.getString(R.string.stylez_enhance),"enhance",R.drawable.enhance),
        StyleModel(app.getString(R.string.stylez_anime),"anime",R.drawable.anime),
        StyleModel(app.getString(R.string.stylez_photographic),"photographic",R.drawable.photographic),
        StyleModel(app.getString(R.string.stylez_digital_art),"digital-art",R.drawable.digital_art),
        StyleModel(app.getString(R.string.stylez_comic_book),"comic-book",R.drawable.comic_book),
        StyleModel(app.getString(R.string.stylez_fantasy_art),"fantasy-art",R.drawable.fantasy_art),
        StyleModel(app.getString(R.string.stylez_line_art),"line-art",R.drawable.line_art),
        StyleModel(app.getString(R.string.stylez_analog_film),"analog-film",R.drawable.analog_film),
        StyleModel(app.getString(R.string.stylez_neon_punk),"neon-punk",R.drawable.neo_punk),
        StyleModel(app.getString(R.string.stylez_isometric),"isometric",R.drawable.isometric),
        StyleModel(app.getString(R.string.stylez_low_poly),"low-poly",R.drawable.low_poly),
        StyleModel(app.getString(R.string.stylez_origami),"origami",R.drawable.origami),
        StyleModel(app.getString(R.string.stylez_modeling_compound),"modeling-compound",R.drawable.modelling_comp),
        StyleModel(app.getString(R.string.stylez_cinematic),"cinematic",R.drawable.cinematic),
        StyleModel(app.getString(R.string.stylez_3d_model),"3d-model",R.drawable.threed_model),
        StyleModel(app.getString(R.string.stylez_pixel_art),"pixel-art",R.drawable.pixel_art),
        StyleModel(app.getString(R.string.stylez_tile_texture),"tile-texture",R.drawable.tile_texture))

}