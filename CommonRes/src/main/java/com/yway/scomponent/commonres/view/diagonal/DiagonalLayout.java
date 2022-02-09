package com.yway.scomponent.commonres.view.diagonal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yway.scomponent.commonres.R;
import com.yway.scomponent.commonres.view.diagonal.manager.ClipPathManager;


//https://github.com/florent37/DiagonalLayout
public class DiagonalLayout extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;
    public static final int DIRECTION_LEFT = 1;

    public static final int DIRECTION_RIGHT = 2;
    @DiagonalPosition
    private int diagonalPosition = POSITION_TOP;
    private int diagonalDirection = POSITION_TOP;

    private float diagonalAngle = 0;
    public DiagonalLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }
    public DiagonalLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DiagonalLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalLayout);
            diagonalAngle = attributes.getFloat(R.styleable.DiagonalLayout_diagonal_angle, diagonalAngle);
            diagonalDirection = attributes.getInteger(R.styleable.DiagonalLayout_diagonal_direction, diagonalDirection);
            diagonalPosition = attributes.getInteger(R.styleable.DiagonalLayout_diagonal_position, diagonalPosition);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                final float perpendicularHeight = (float) (width * Math.tan(Math.toRadians(diagonalAngle)));
                final boolean isDirectionLeft = diagonalDirection == DIRECTION_LEFT;

                switch (diagonalPosition) {
                    case POSITION_BOTTOM:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingRight());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.close();
                        }
                        break;
                    case POSITION_TOP:
                        if (isDirectionLeft) {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_RIGHT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_LEFT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft() + perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft() + perpendicularHeight, height - getPaddingBottom());
                            path.close();
                        }
                        break;
                }
                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    public int getDiagonalPosition() {
        return diagonalPosition;
    }

    public void setDiagonalPosition(@DiagonalPosition int diagonalPosition) {
        this.diagonalPosition = diagonalPosition;
        requiresShapeUpdate();
    }

    public int getDiagonalDirection() {
        return diagonalDirection;
    }

    public void setDiagonalDirection(int diagonalDirection) {
        this.diagonalDirection = diagonalDirection;
        requiresShapeUpdate();
    }

    public float getDiagonalAngle() {
        return diagonalAngle;
    }

    public void setDiagonalAngle(float diagonalAngle) {
        this.diagonalAngle = diagonalAngle;
        requiresShapeUpdate();
    }

    @IntDef(value = {POSITION_BOTTOM, POSITION_TOP, POSITION_LEFT, POSITION_RIGHT})
    public @interface DiagonalPosition {
    }

    @IntDef(value = {DIRECTION_LEFT, DIRECTION_RIGHT})
    public @interface DiagonalDirection {
    }
}
