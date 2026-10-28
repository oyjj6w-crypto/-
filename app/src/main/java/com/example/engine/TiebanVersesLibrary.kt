package com.example.engine

import com.example.model.FortuneLevel

object TiebanVersesLibrary {

    data class VerseTemplate(
        val originalVerse: String,
        val originalJudgment: String,
        val correctedVerse: String,
        val correctedJudgment: String,
        val aspect: String,
        val fortuneLevel: FortuneLevel
    )

    // 核心经典铁板神数条文与校正详注集萃 (精选高频代表性命理条文与古今考订注解)
    val VERSE_TEMPLATES = listOf(
        VerseTemplate(
            originalVerse = "春风吹拂柳条新，喜气盈门万象春。数定此时名显达，千祥云集福随身。",
            originalJudgment = "岁逢文昌，求名有喜，门庭生辉。",
            correctedVerse = "春风化育柳条新，喜气盈门百草春。数合天机名望起，千祥云集顺行身。",
            correctedJudgment = "此年诸事顺遂，智慧通达。学业考学必占鳌头，职场求官利于考评晋升；利于开拓新业务，贵人多在东南方向。",
            aspect = "学业仕途",
            fortuneLevel = FortuneLevel.GREAT_LUCK
        ),
        VerseTemplate(
            originalVerse = "数逢天乙贵人迎，不用劳心百事成。财源茂盛如同涌，利禄丰盈家道荣。",
            originalJudgment = "贵人照命，利官进财，百事亨通。",
            correctedVerse = "数注吉星贵客临，从容谋度事皆成。财基广积若泉涌，家道安和万绪清。",
            correctedJudgment = "运势如日中天，人际网络拓展迅速，常得尊长或权要提携借力。财务收益大幅增长，适宜稳健置产或长线布局。",
            aspect = "财源盛景",
            fortuneLevel = FortuneLevel.GREAT_LUCK
        ),
        VerseTemplate(
            originalVerse = "行至运途逢水木，浮沉世态莫嗟嘘。安常守己无灾悔，待得天时自起予。",
            originalJudgment = "运势平平，凡事宜守，不可轻进。",
            correctedVerse = "行临中道遇流澜，进退从容意自宽。抱朴守真远虞咎，静待春雷发岁端。",
            correctedJudgment = "流年气运潜伏，多属于积累沉淀之年。外部合作切忌冲动注资，宜修养身心、巩固内部管理或专攻专业技能。",
            aspect = "守成韬晦",
            fortuneLevel = FortuneLevel.MODERATE
        ),
        VerseTemplate(
            originalVerse = "红鸾星动喜重重，良缘天定室家融。琴瑟和谐歌雅韵，弄璋弄瓦庆门中。",
            originalJudgment = "红鸾天喜照临，婚嫁添丁之吉。",
            correctedVerse = "鸾凤和鸣景物隆，佳期谐合乐融融。家敦笃友增祥瑞，兰玉盈阶吉庆中。",
            correctedJudgment = "感情运极佳，单身者易遇良缘正配，恋人相知可步入婚姻殿堂；已婚者家庭和睦、喜得麟儿或家眷乔迁添福。",
            aspect = "姻缘添丁",
            fortuneLevel = FortuneLevel.GOOD_LUCK
        ),
        VerseTemplate(
            originalVerse = "风波微起浪中行，防慎小人暗里争。谨慎自持无大患，收敛锋芒得康宁。",
            originalJudgment = "小耗交攻，宜防口舌，莫与人争。",
            correctedVerse = "风微动浪泛扁舟，暗昧是非须细由。慎诺谨言消疑隙，潜心秉德免烦愁。",
            correctedJudgment = "流年见口舌是非干扰，职场上需注意文书合同细节，切勿充当担保人；凡事以和为贵，低调行事即可化险为夷。",
            aspect = "人际防惕",
            fortuneLevel = FortuneLevel.CAUTION
        ),
        VerseTemplate(
            originalVerse = "太岁临门动吉星，迁移改换必称情。出外求财多遂意，龙腾沧海显威名。",
            originalJudgment = "驿马逢吉，动中生财，迁变大利。",
            correctedVerse = "时逢转动合吉星，迁居拓业两称心。扬帆万里乘风去，步履坦然显英名。",
            correctedJudgment = "运入驿马变动之机，多有远行、出差、工作调动或迁居换房之兆；主动求变者生发新机，走出去方能广开财路。",
            aspect = "行运迁动",
            fortuneLevel = FortuneLevel.CHANGE
        ),
        VerseTemplate(
            originalVerse = "积善之家庆有余，经纶济世步云衢。福禄双全天意定，晚景从容自裕如。",
            originalJudgment = "福德庇佑，家业光大，德厚流光。",
            correctedVerse = "敦行积善庆有余，文质相辉入翠衢。厚德自能承福禄，悠然气象远荣枯。",
            correctedJudgment = "德厚福隆之象，个人声誉与社会地位显著攀升。多做公益善事，利于门风昌盛、子孙荣发，身心舒畅安泰。",
            aspect = "名誉家声",
            fortuneLevel = FortuneLevel.GOOD_LUCK
        ),
        VerseTemplate(
            originalVerse = "金水相生润泽深，营谋顺适称中心。旧仇冰释逢良友，玉润珠明满座吟。",
            originalJudgment = "五行相生，合气吉祥，百谋克成。",
            correctedVerse = "金润源泉理致深，所求谐适适吾心。化解前嫌增胜友，满堂和乐共长吟。",
            correctedJudgment = "五行相生有情，人际沟通格外顺畅，曾有误会的朋友亦能重归于好；极利于团队协作、商务洽谈与联合投资。",
            aspect = "商洽交游",
            fortuneLevel = FortuneLevel.GOOD_LUCK
        ),
        VerseTemplate(
            originalVerse = "乌云散尽月华明，数定荣华指日生。莫道中年多蹭蹬，而今脱网任高征。",
            originalJudgment = "困顿已去，转运初临，鹏程万里。",
            correctedVerse = "烟云散尽霁光明，万象逢春泰运生。休论往昔风霜苦，展翼长空振羽鸣。",
            correctedJudgment = "历经先前考验与磨难后，终于迎来转折上升期。旧问题迎刃而解，思路豁然开朗，可大胆实施筹备已久的宏图。",
            aspect = "困局逆转",
            fortuneLevel = FortuneLevel.GREAT_LUCK
        ),
        VerseTemplate(
            originalVerse = "一轮明月映长空，心静自然百疾空。善摄调和宜静养，松椿挺秀乐从容。",
            originalJudgment = "养性延年，节制调养，起居有度。",
            correctedVerse = "清池皓月照虚空，神定安和体自松。顺应四时加调护，岁华长寿乐融融。",
            correctedJudgment = "流年利于身心养护与修身养性。宜规律起居，饮食清淡，少劳心思虑；有微恙者顺应节气调理即迅速恢复强健。",
            aspect = "健康福寿",
            fortuneLevel = FortuneLevel.MODERATE
        ),
        VerseTemplate(
            originalVerse = "财帛宫中照福星，买卖经营利倍增。四海宾朋交接广，金银积玉富千层。",
            originalJudgment = "财帛大旺，经营得利，获财丰厚。",
            correctedVerse = "财库开泰启福星，运筹运度利倍增。四海贤才交际广，稳筑丰仓业日升。",
            correctedJudgment = "正偏财皆具良机，善于发现市场空白或商业先机。与人合作诚信第一，账目清晰，可聚财富盈箱。",
            aspect = "置业生财",
            fortuneLevel = FortuneLevel.GREAT_LUCK
        ),
        VerseTemplate(
            originalVerse = "江上孤舟逢逆水，岸边杨柳锁寒烟。休向险中求利禄，静观潮汐待丰年。",
            originalJudgment = "临险莫进，防损阻滞，安待其时。",
            correctedVerse = "扁舟行浅慎逆潮，岸柳微寒锁暮萧。勿履虚薄追幻利，宁心固本待回韶。",
            correctedJudgment = "年运略显迟滞，切忌被高利诱惑冒进投机；行车安全及骨骼关节宜加留意，稳步推进当前本职工作即可避虚就实。",
            aspect = "防微杜渐",
            fortuneLevel = FortuneLevel.CAUTION
        )
    )

    val TONE_MARKERS = listOf(
        "宫平甲一", "宫上乙二", "宫去丙三", "宫入丁四",
        "商平戊五", "商上己六", "商去庚七", "商入辛八",
        "角平壬九", "角上癸十", "角去甲一", "角入乙二",
        "徵平丙三", "徵上丁四", "徵去戊五", "徵入己六",
        "羽平庚七", "羽上辛八", "羽去壬九", "羽入癸十"
    )

    fun getTemplate(hashKey: Int): VerseTemplate {
        val index = (Math.abs(hashKey) % VERSE_TEMPLATES.size)
        return VERSE_TEMPLATES[index]
    }

    fun getToneMarker(hashKey: Int): String {
        val index = (Math.abs(hashKey) % TONE_MARKERS.size)
        return TONE_MARKERS[index]
    }
}
