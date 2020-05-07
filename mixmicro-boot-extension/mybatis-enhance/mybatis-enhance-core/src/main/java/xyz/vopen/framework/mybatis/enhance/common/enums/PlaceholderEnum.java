/**
 * The MIT License (MIT)
 *
 * <p>Copyright (c) 2018 VOPEN.XYZ
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.vopen.framework.mybatis.enhance.common.enums;

public enum PlaceholderEnum {
  EQ(" = "),
  NEQ(" <> "),
  ALL(" * "),
  LT(" < "),
  GT(" > "),
  LET(" <= "),
  GET(" >= "),
  LIKE(" LIKE "),
  LIMIT(" LIMIT "),
  SPLIT(" , "),
  AS("AS"),
  IN(" IN "),
  AND(" AND "),
  OR(" OR "),
  SET(" SET "),
  IS_NOT_NULL(" IS NOT NULL "),
  IS_NULL(" IS NULL "),
  IS_EMPTY(" = '' "),
  IS_NOT_EMPTY(" <> '' "),
  INSERT_INTO("INSERT INTO "),
  DELETE_FROM("DELETE FROM "),
  SELECT("SELECT "),
  COUNT("COUNT"),
  FROM(" FROM "),
  ORDER_BY("ORDER BY "),
  UPDATE("UPDATE "),
  VALUES(" ) VALUES "),
  SPLIT_PREFIX(" ( "),
  SPLIT_SUFFIX(" ) "),
  WHERE(" WHERE ");
  private String value;

  PlaceholderEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
