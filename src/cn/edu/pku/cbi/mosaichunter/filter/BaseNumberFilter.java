package cn.edu.pku.cbi.mosaichunter.filter;

import cn.edu.pku.cbi.mosaichunter.Site;
import cn.edu.pku.cbi.mosaichunter.config.ConfigManager;

public class BaseNumberFilter extends BaseFilter {

    public static final int DEFAULT_MIN_MINOR_ALLELE_NUMBER = 5;
    public static final double DEFAULT_MIN_MINOR_ALLELE_PERCENTAGE = 5;
    public static final double DEFAULT_MAX_MINOR_ALLELE_PERCENTAGE = 100;
    
    private final int minMinorAlleleNumber;
    private final double minMinorAllelePercentage;
    private final double maxMinorAllelePercentage;
    
    public BaseNumberFilter(String name) {
        this(name,
             ConfigManager.getInstance().getInt(
                     name, "min_minor_allele_number", DEFAULT_MIN_MINOR_ALLELE_NUMBER),
             ConfigManager.getInstance().getDouble(
                     name, "min_minor_allele_percentage", DEFAULT_MIN_MINOR_ALLELE_PERCENTAGE),
             ConfigManager.getInstance().getDouble(
                     name, "max_minor_allele_percentage", DEFAULT_MAX_MINOR_ALLELE_PERCENTAGE));
    }
    
    public BaseNumberFilter(String name, int minMinorAlleleNumber, 
            double minMinorAllelePercentage, double maxMinorAllelePercentage) {
        super(name);
        this.minMinorAlleleNumber = minMinorAlleleNumber;
        this.minMinorAllelePercentage = minMinorAllelePercentage / 100.0;
        this.maxMinorAllelePercentage = maxMinorAllelePercentage / 100.0;
    }
    
    @Override
    public boolean doFilter(Site site) {
        return site.getMinorAlleleCount() >= minMinorAlleleNumber &&
               site.getMinorAlleleCount() >= 
                       site.getDepth() * minMinorAllelePercentage &&
               site.getMinorAlleleCount() <
                       site.getDepth() * maxMinorAllelePercentage;
    }
    
}